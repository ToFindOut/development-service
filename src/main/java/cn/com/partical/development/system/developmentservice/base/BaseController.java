package cn.com.partical.development.system.developmentservice.base;


import cn.com.partical.development.system.developmentservice.util.cache.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhangWang
 * @date 2020/8/17 10:33
 */
public class BaseController {

    @Autowired
    private RedisUtil redisUtil;

    protected Long getUserId(HttpServletRequest request) {

        if (StringUtils.isBlank(request.getHeader("token")) || redisUtil.getValue(request.getHeader("token")) == null) {
            return null;
        } else {
            return Long.valueOf(redisUtil.getValue(request.getHeader("token")).toString());
    }

    }


}
