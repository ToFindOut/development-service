package cn.com.partical.development.system.developmentservice.util.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtil：Redis工具类
 * @author：Alex
 * @create：2019-08-23-15:31
 */
@SuppressWarnings("unchecked")
@Component
public class RedisUtil {
    /**
     * redisTemplate：redisTemplate
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    //////////////////////////// base start ////////////////////////////
    /**
     * openTransaction：开启事务
     */
    public void openTransaction() {
        // 开启Redis 事务权限
        redisTemplate.setEnableTransactionSupport(true);
        // 开启事务
        redisTemplate.multi();
    }

    /**
     * commitTransaction：提交事务
     */
    public void commitTransaction() {
        // 成功提交事务
        redisTemplate.exec();
    }

    /**
     * rollbackTransaction：回滚事务
     */
    public void rollbackTransaction() {
        //
        redisTemplate.discard();
    }
    //////////////////////////// base end //////////////////////////////


    // ================================================================ //

    //////////////////////////// global start ////////////////////////////
    /**
     * del：删除缓存
     * @param key 键，可以传一个值或多个
     * @return 成功删除的数量
     */
//    public long del(String... key) {
//        if (StringUtils.isAnyBlank(key)) {
//            throw new RuntimeException("key不能为空");
//        }
//        return redisTemplate.delete(CollectionUtils.arrayToList(key));
//    }

    /**
     * 模糊删除
     * @param prex
     */
    public void deleteByPrex(String prex) {
        Set<String> keys = redisTemplate.keys(prex);
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * setExpire：设置缓存失效时间
     * @param key 键
     * @param time 失效时间，单位秒
     * @return 设置状态 true 设置成功 false 设置失败
     */
    public Boolean setExpire(String key, long time) {
        if (StringUtils.isAnyBlank(key)) {
            throw new RuntimeException("key不能为空且time不能小于0");
        }
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * getExpire：读取缓存剩余失效时间
     * @param key 键
     * @return 缓存剩余失效时间，单位秒，返回0代表为永久有效
     */
    public long getExpire(String key) {
        if (StringUtils.isAnyBlank(key)) {
            throw new RuntimeException("key不能为空");
        }
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * hasKey：判断键是否存在
     * @param key 键
     * @return 是否存在 true 存在 false 不存在
     */
    public Boolean hasKey(String key) {
        if (StringUtils.isAnyBlank(key)) {
            throw new RuntimeException("key不能为空");
        }
        return redisTemplate.hasKey(key);
    }

    //////////////////////////// global end //////////////////////////////

    // ================================================================ //

    //////////////////////////// string start ////////////////////////////
    /**
     * setValue：设置常规类型的值
     * @param key 键
     * @param value 值
     */
    public void setValue(String key, Object value) {
        if (StringUtils.isAnyBlank(key)) {
            throw new RuntimeException("key不能为空");
        }
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * setValue：设置常规类型的值
     * @param key 键
     * @param value 值
     * @param time 失效时间，单位秒
     */
    public void setValue(String key, Object value, long time) {
        setValue(key, value);
        setExpire(key, time);
    }

    /**
     * incValue：更改常规类型的值
     *          值相加
     * @param key 键
     * @param delta 相加值
     * @return 相加之后的结果
     */
    public long incValue(String key, long delta) {
        if (StringUtils.isAnyBlank(key) || delta < 0) {
            throw new RuntimeException("key不能为空且delta必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * decValue：更改常规类型的值
     *          值相减
     * @param key 键
     * @param delta 相减值
     * @return 相减之后的结果
     */
    public long decValue(String key, long delta) {
        if (StringUtils.isAnyBlank(key) || delta < 0) {
            throw new RuntimeException("key不能为空且delta必须大于0");
        }
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * getValue：获取常规类型的值
     * @param key 键
     * @return 值
     */
    public Object getValue(String key) {
        if (StringUtils.isAnyBlank(key)) {
            throw new RuntimeException("key不能为空");
        }
        return redisTemplate.opsForValue().get(key);
    }

    //////////////////////////// string end //////////////////////////////

    // ================================================================ //

    //////////////////////////// hash Start ////////////////////////////
    /**
     * setHash：设置Hash类型的值
     * @param key 键
     * @param map 值
     */
    public void setHash(String key, Map<Object, Object> map) {
        if (StringUtils.isAnyBlank(key) || map == null) {
            throw new RuntimeException("键和值均不能为空");
        }
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * setHash：设置Hash类型的值
     * @param key 键
     * @param map 值
     * @param time 失效时间，单位秒
     */
    public void setHash(String key, Map<Object, Object> map, long time) {
        setHash(key, map);
        setExpire(key, time);
    }

    /**
     * setHash：设置Hash类型的值
     * @param key 键
     * @param item Hash键
     * @param value Hash值
     */
    public void setHash(String key, Object item, Object value) {
        if (StringUtils.isAnyBlank(key) || item == null || value == null) {
            throw new RuntimeException("键、map项和值均不能为空");
        }
        redisTemplate.opsForHash().put(key, item, value);
    }

    /**
     * setHash：设置Hash类型的值
     * @param key 键
     * @param item Hash键
     * @param value Hash值
     * @param time 失效时间，单位秒
     */
    public void setHash(String key, Object item, Object value, long time) {
        setHash(key, item, value);
        setExpire(key, time);
    }

    /**
     * delHash：删除Hash类型的值
     * @param key 键
     * @param item Hash键，可以传一个值或多个
     * @return 成功删除的数量
     */
    public long delHash(String key, Object... item) {
        if (StringUtils.isAnyBlank(key) || item == null || item.length == 0) {
            throw new RuntimeException("key和item均不能为空");
        }
        return redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * incHash：更改Hash类型的值
     *              值相加
     * @param key 键
     * @param item Hash键
     *             如果项不存在则自动创建
     * @param delta 相加的数量
     * @return 相加之后的结果
     */
    public double incHash(String key, Object item, double delta) {
        if (StringUtils.isAnyBlank(key) || item == null || delta < 0) {
            throw new RuntimeException("key和item均不能为空且delta必须大于0");
        }
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * decHash：更改Hash类型的值
     *              值相减
     * @param key 键
     * @param item Hash键
     *             如果项不存在则自动创建
     * @param delta 相减的数量
     * @return 相减之后的结果
     */
    public double decHash(String key, String item, double delta) {
        if (StringUtils.isAnyBlank(key) || item == null || delta < 0) {
            throw new RuntimeException("key和item均不能为空且delta必须大于0");
        }
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

    /**
     * getHash：获取Hash类型的值
     * @param key 键
     * @return 值
     */
    public Map<Object, Object> getHash(String key) {
        if (StringUtils.isAnyBlank(key)) {
            throw new RuntimeException("key不能为空");
        }
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * getHash：获取Hash类型的值
     * @param key 键
     * @param item Hash键
     * @return Hash值
     */
    public Object getHash(String key, Object item) {
        if (StringUtils.isAnyBlank(key) || item == null) {
            throw new RuntimeException("key和item均不能为空");
        }
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * hasHash：判断Hash类型的键是否存在
     * @param key 键
     * @param item Hash键
     * @return true 存在 false 不存在
     */
    public boolean hasHash(String key, Object item) {
        if (StringUtils.isAnyBlank(key) || item == null) {
            throw new RuntimeException("key和item均不能为空");
        }
        return redisTemplate.opsForHash().hasKey(key, item);
    }
    //////////////////////////// hash end //////////////////////////////

    // ================================================================ //

    //////////////////////////// set start ////////////////////////////
    /**
     * setSet：设置Set类型的值
     * @param key 键
     * @param values 值, 可以传一个值或多个
     */
    public void setSet(String key, Object... values) {
        if (StringUtils.isAnyBlank(key) || values == null || values.length == 0) {
            throw new RuntimeException("key和values均不能为空");
        }
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * setSet：设置Set类型的值
     * @param key 键
     * @param time 失效时间，单位秒
     * @param values 值, 可以传一个值或多个
     * @return 结果 true 成功 false 失败
     */
    public void setSet(String key, long time, Object... values) {
        setSet(key, values);
        setExpire(key, time);
    }

    /**
     * decSet：删除Set类型的值
     * @param key 键
     * @param values 值，可以传一个值或多个
     * @return 成功删除的数量
     */
    public long delSet(String key, Object... values) {
        if (StringUtils.isAnyBlank(key) || values == null || values.length == 0) {
            throw new RuntimeException("key和values均不能为空");
        }
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * getSet：获取Set类型的值
     * @param key 键
     * @return 值
     */
    public Set<Object> getSet(String key) {
        if (StringUtils.isAnyBlank(key)) {
            throw new RuntimeException("key不能为空");
        }
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * getSetSize：获取Set类型的值的大小
     * @param key 键
     * @return 大小
     */
    public long getSetSize(String key) {
        if (StringUtils.isAnyBlank(key)) {
            throw new RuntimeException("key不能为空");
        }
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * hasSet：判断Set类型的值是否存在
     * @param key 键
     * @param item 指定的值
     * @return 是否存在 true 存在 false 不存在
     */
    public boolean hasSet(String key, Object item) {
        if (StringUtils.isAnyBlank(key) || item == null) {
            throw new RuntimeException("key和item均不能为空");
        }
        return redisTemplate.opsForSet().isMember(key, item);
    }
    //////////////////////////// set end //////////////////////////////

    // ================================================================ //

    //////////////////////////// list start ////////////////////////////
    /**
     * setList：设置List类型的值
     * @param key 键
     * @param value 值
     * @param rightPush 是否在列表右侧添加
     */
    public void setList(String key, Object value, boolean rightPush) {
        if (StringUtils.isAnyBlank(key) || value == null) {
            throw new RuntimeException("key和value均不能为空");
        }
        if (rightPush) {
            redisTemplate.opsForList().rightPush(key, value);
        } else {
            redisTemplate.opsForList().leftPush(key, value);
        }
    }

    /**
     * setList：设置List类型的值
     * @param key 键
     * @param value 值
     * @param rightPush 是否在列表右侧添加
     * @param time 失效时间，单位秒
     */
    public void setList(String key, Object value, boolean rightPush, long time) {
        setList(key, value, rightPush);
        setExpire(key, time);
    }

    /**
     * setList：设置List类型的值
     * @param key 键
     * @param value 值
     * @param rightPush 是否在列表右侧添加
     */
    public void setList(String key, List<Object> value, boolean rightPush) {
        if (StringUtils.isAnyBlank(key) || value == null) {
            throw new RuntimeException("key和value均不能为空");
        }
        if (rightPush) {
            redisTemplate.opsForList().rightPushAll(key, value);
        } else {
            redisTemplate.opsForList().leftPushAll(key, value);
        }
    }

    /**
     * setList：设置List类型的值
     * @param key 键
     * @param value 值
     * @param rightPush 是否在列表右侧添加
     * @param time 失效时间，单位秒
     */
    public void setList(String key, List<Object> value, boolean rightPush, long time) {
        setList(key, value, rightPush);
        setExpire(key, time);
    }

    /**
     * delList：删除List类型的值
     * @param key 键
     * @param count 要删除的数量
     *              当 count > 0 时，数据从左往右删除
     *              当 count < 0 时，数据从右往左删除
     *              当 count = 0 时，删除全部数据
     * @param value 要删除的值
     * @return
     */
    public long delList(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * updateList：修改List中的数据
     *              根据Index索引修改，索引从0开始
     * @param key 键
     * @param index 索引，索引从0开始
     * @param value 修改的值
     */
    public void updateList(String key, long index, Object value) {
        if (StringUtils.isAnyBlank(key) || index < 0 || value == null) {
            throw new RuntimeException("key和value均不能为空且index不能小于0");
        }
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * getList：获取List类型的值
     * @param key 键
     * @param start 开始的索引，从0开始
     * @param end 结束的索引，从0开始
     * @return 结果列表
     */
    public List<Object> getList(String key, long start, long end) {
        if (StringUtils.isAnyBlank(key) || start < 0 || end < 0 || start > end) {
            throw new RuntimeException("key不能为空，start和end均不能小于0且start不能大于end");
        }
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * getList：获取List类型的值
     * @param key 键
     * @param index 指定的索引，从0开始
     * @return 结果对象
     */
    public Object getList(String key, long index) {
        if (StringUtils.isAnyBlank(key) || index < 0) {
            throw new RuntimeException("key不能为空且index不能小于0");
        }
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * getListSize：获取List类型值的大小
     * @param key 键
     * @return 大小
     */
    public Long getListSize(String key) {
        if (StringUtils.isAnyBlank(key)) {
            throw new RuntimeException("key不能为空");
        }
        return redisTemplate.opsForList().size(key);
    }
    //////////////////////////// list end //////////////////////////////

    //bitmap操作
    /**
     * setbit 设置一个值
     *
     * @param key
     * @param offset
     * @param value
     */
    public void setBit(String key, Long offset, Boolean value) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("redis key is null");
        }
        redisTemplate.execute((RedisCallback<Boolean>) con -> con.setBit(key.getBytes(),offset , value));
    }

    /**
     * getBit
     * 获取指定offset的值
     *
     * @param key
     * @param offset
     * @return
     */

    public Boolean getBit(String key, Integer offset) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("redis key is null");
        }
        return redisTemplate.execute((RedisCallback<Boolean>) con -> con.getBit(key.getBytes(), offset));

    }

    /**
     * bitcount
     * @param key
     * @return
     */
    public Long bitCount(String key) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes())).longValue();
    }

    public Long bitOp(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
        byte[][] bytes = new byte[desKey.length][];
        for (int i = 0; i < desKey.length; i++) {
            bytes[i] = desKey[i].getBytes();
        }
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(op, saveKey.getBytes(), bytes));
    }

    /**
     * HyperLogLog 基数去重
     * @param key
     * @param value
     * @return
     */
    public Long pfadd(String key, Object... value) {
        return redisTemplate.opsForHyperLogLog().add(key, value);
    }

    /**
     * HyperLogLog 统计总数
     * @param key
     * @return
     */
    public Integer pfcount(String key) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.pfCount(key.getBytes())).intValue();
    }


    //锁名称前缀
    public static final String LOCK_PREFIX = "redis_lock";
    //加锁失效时间，毫秒
    public static final int LOCK_EXPIRE = 600; // ms

    /**
     * 获得锁
     *
     * @param lock
     * @return
     */
    public boolean lock(String lock) {
        return (boolean) redisTemplate.execute((RedisCallback) connection -> {
            //获取时间毫秒值
            long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
            //获取锁
            Boolean acquire = connection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());
            if (acquire) {
                return true;
            } else {
                byte[] bytes = connection.get(lock.getBytes());
                //非空判断
                if (Objects.nonNull(bytes) && bytes.length > 0) {
                    long expireTime = Long.parseLong(new String(bytes));
                    // 如果锁已经过期
                    if (expireTime < System.currentTimeMillis()) {
                        // 重新加锁，防止死锁
                        byte[] set = connection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE + 1).getBytes());
                        return Long.parseLong(new String(set)) < System.currentTimeMillis();
                    }
                }
            }
            return false;
        });
    }


    /**
     * 删除锁
     *
     * @param key
     */
    public void deleteLock(String key) {
        redisTemplate.delete(key);
    }

}
