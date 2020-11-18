package cn.com.partical.development.system.developmentservice.common.constant;

public interface UserConstant {

    /**
     * 登录token前缀
     */
    String LOGIN_TOKEN_PREFIX = "LOGIN_TOKEN_PREFIX_";

    /**
     * 手机验证码前缀
     */
    String PHONE_VERIFICATION_PREFIX = "PHONE_VERIFICATION_PREFIX_";

    /**
     * 模糊符号
     */
    String FUZZY_SYMBOL = "*";

    /**
     * 下划线
     */
    String UNDERLINE = "_";

    /**
     * 密码长度
     */
    int PASSWORD_LENGTH = 6;

    /**
     * 手机验证码过期时间
     */
    int PHONE_VERIFICATION_EXPIRATION_TIME = 300;

    /**
     * 注册验证码状态
     */
    int VERIFICATION_REGISTER_CODE = 1;

    /**
     * 找回密码验证码状态
     */
    int VERIFICATION_RETRIEVE_PASSWORD_CODE = 2;
}
