package cn.com.partical.development.system.developmentservice.common.global;

import lombok.Data;

/**
 * GlobalApiResponse：微服务接口统一返回码
 * @author：Alex
 * @create：2019-08-19 10:37
 */
@Data
@SuppressWarnings("unchecked")
public class GlobalApiResponse<T> {
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 消息c
     */
    private String msg;
    /**
     * 返回
     */
    private T data;
    // 分页

    public GlobalApiResponse() {
    }

    public GlobalApiResponse(Integer code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
