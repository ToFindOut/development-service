package cn.com.partical.development.system.developmentservice.util.api;


import cn.com.partical.development.system.developmentservice.common.global.GlobalApiConstants;
import cn.com.partical.development.system.developmentservice.common.global.GlobalApiResponse;

/**
 * ResponseUtil：response工具类
 * @author：Alex
 * @create：2019-08-19 10:42
 */
@SuppressWarnings("unchecked")
public class ResponseUtil<T> {
    /**
     * success：返回错误
     * @return 响应信息
     */
    public static <T> GlobalApiResponse error() {
        return response(GlobalApiConstants.HTTP_RES_CODE_500, GlobalApiConstants.HTTP_RES_CODE_500_VALUE, null);
    }

    /**
     * success：返回错误
     * @param data 数据
     * @return 响应信息
     */
    public static <T> GlobalApiResponse error(String msg, T data) {
        return response(GlobalApiConstants.HTTP_RES_CODE_500, msg, data);
    }

    /**
     * error：返回错误
     * @param code
     * @param msg 提示消息
     * @return 响应信息
     */
    public static <T> GlobalApiResponse error(Integer code, String msg) {
        return response(code, msg, null);
    }

    /**
     * error：返回错误
     * @param msg 提示消息
     * @return 响应信息
     */
    public static <T> GlobalApiResponse error(String msg) {
        return response(GlobalApiConstants.HTTP_RES_CODE_500, msg, null);
    }

    /**
     * success：返回成功
     * @param data 数据
     * @return 响应信息
     */
    public static <T> GlobalApiResponse success(String msg, T data) {
        return response(GlobalApiConstants.HTTP_RES_CODE_200, msg, data);
    }

    /**
     * success：返回成功
     * @param data 数据
     * @return 响应信息
     */
    public static <T> GlobalApiResponse success(T data) {
        return response(GlobalApiConstants.HTTP_RES_CODE_200, GlobalApiConstants.HTTP_RES_CODE_200_VALUE, data);
    }

    /**
     * success：返回成功
     * @return 响应信息
     */
    public static <T> GlobalApiResponse success() {
        return response(GlobalApiConstants.HTTP_RES_CODE_200, GlobalApiConstants.HTTP_RES_CODE_200_VALUE, null);
    }

    /**
     * success：返回成功
     * @param msg 提示消息
     * @return 响应信息
     */
    public static <T> GlobalApiResponse success(String msg) {
        return response(GlobalApiConstants.HTTP_RES_CODE_200, msg, null);
    }

    /**
     * response：通用封装
     * @param code 编码
     * @param msg 提示消息
     * @param data 数据
     * @return 响应信息
     */
    public static <T> GlobalApiResponse response(Integer code, String msg, T data) {
        return new GlobalApiResponse(code, msg, data);
    }
}
