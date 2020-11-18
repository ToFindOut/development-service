package cn.com.partical.development.system.developmentservice.util.security;


import cn.com.partical.development.system.developmentservice.common.global.GlobalException;
import com.github.qcloudsms.SmsSingleSender;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 发送短信验证码
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/18 14:23
 */
public class SendSmsVerificationCode {

    private final static int APP_ID = 1400334087;
    private final static String APP_KEY = "05e9010b33424554f119009372c5a8f7";
    private final static String SMS_SIGN = "智能教育";
    private final static int TEMPLATE_ID = 557037;

    /**
     * 通过手机号发送短信验证码
     * @param phone 手机号
     * @return 验证码
     */
    public static String sendSmsVerificationCodeViaPhoneNumber(String phone) {

        String code = RandomStringUtils.randomNumeric(4);
        String[] x = {code, "5"};

        SendSmsVerificationCode.sendSms(phone,  x);

        return code;
    }

    /**
     * 发送短信验证码
     * @param phoneNo 手机号
     * @param params 参数
     */
    private static void sendSms(String phoneNo, String[] params){

        SmsSingleSender ssender = new SmsSingleSender(APP_ID, APP_KEY);
        try {
            ssender.sendWithParam("86",phoneNo,TEMPLATE_ID, params, SMS_SIGN, "", "");

        } catch (Exception e) {
            throw new GlobalException(801, "发送失败,网络异常");
        }
    }
}
