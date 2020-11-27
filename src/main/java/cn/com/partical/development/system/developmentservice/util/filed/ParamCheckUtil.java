package cn.com.partical.development.system.developmentservice.util.filed;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/26 10:36
 */
public class ParamCheckUtil {

    /**
     * 判断该对象的一部分属性是否全部为空
     * @param obj 对象
     * @param excludeNames 选择忽略校验的属性名称List集合
     * @return boolean
     */
    public static boolean isAllFieldNull(Object obj, List<String> excludeNames) {
        // 取到obj的class, 并取到所有属性
        Field[] fs = obj.getClass().getDeclaredFields();
        // 定义一个flag, 标记是否所有属性值为空
        boolean flag = true;
        try {
            // 遍历所有属性
            for (Field f : fs) {
                // 设置私有属性也是可以访问的
                f.setAccessible(true);
                // 1.排除不包括的属性名, 2.属性值不为空, 3.属性值转换成String不为""
                if(!excludeNames.contains(f.getName()) && f.get(obj) != null && !"".equals(f.get(obj).toString())) {
                    // 有属性满足3个条件的话, 那么说明对象属性不全为空
                    flag = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }
}
