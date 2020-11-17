package cn.com.partical.development.system.developmentservice.common.global;



import cn.com.partical.development.system.developmentservice.util.api.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * GlobalExceptionHandler：全局异常捕获
 * @author：Alex
 * @create：2019-08-19 10:56
 */
@SuppressWarnings("unchecked")
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * exceptionHandler：拦截自定义全局异常信息
     * @param e 异常
     * @return 结果
     */
    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public GlobalApiResponse<String> exceptionHandler(GlobalException e) {
        return ResponseUtil.error(e.getCode(), e.getMessage());
    }

    /**
     * exceptionHandler：拦截RuntimeException信息
     * @param e 异常
     * @return 结果
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public GlobalApiResponse<String> exceptionHandler(RuntimeException e) {
        log.info("###全局捕获异常2###,error:{}", e);
//        globalExceptionSend.sendMail(e);
        return ResponseUtil.error("系统错误!");
    }

    /**
     * exceptionHandler：拦截其它异常信息
     * @param e 异常
     * @return 结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public GlobalApiResponse<String> exceptionHandler(Exception e) {
        log.info("###全局捕获异常3###,error:{}", e);
//        globalExceptionSend.sendMail(e);
        return ResponseUtil.error("系统错误!");
    }
}
