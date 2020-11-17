package cn.com.partical.development.system.developmentservice.common.global;

import lombok.Data;

/**
 * GlobalException：全局异常信息
 * @author：Alex
 * @create：2019-08-19-10:55
 */
@Data
@SuppressWarnings("unchecked")
public class GlobalException extends RuntimeException {
    private Integer code = 500;
    public GlobalException() {
        super();
    }
    public GlobalException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public GlobalException(String message) {
        super(message);
    }
    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }
    public GlobalException(Throwable cause) {
        super(cause);
    }
    protected GlobalException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
