package cn.com.partical.development.system.developmentservice.common.constant;

public interface IUserConstant {

    /************************ Redis key 前缀 *****************************/
    // 登录token前缀
    String LOGIN_TOKEN_PREFIX = "LOGIN_TOKEN_PREFIX_";
    /**
     * 注册手机验证码前缀
     */
    String REGISTER_PHONE_VERIFICATION_PREFIX = "REGISTER_PHONE_VERIFICATION_PREFIX_";
    /**
     * 找回密码验证码前缀
     */
    String RETRIEVE_PASSWORD_VERIFICATION_PREFIX = "RETRIEVE_PASSWORD_VERIFICATION_PREFIX_";
    /**
     * 修改手机号前缀
     */
    String UPDATE_PHONE_VERIFICATION_PREFIX = "UPDATE_PHONE_VERIFICATION_PREFIX_";



    /************************ 验证码 *****************************/
    // 注册验证码状态
    int VERIFICATION_REGISTER_CODE = 1;
    /**
     * 找回密码验证码状态
     */
    int VERIFICATION_RETRIEVE_PASSWORD_CODE = 2;

    /**
     * 修改手机验证码状态
     */
    int UPDATE_PHONE_VERIFICATION_CODE = 3;



    /************************ 性别 *****************************/
    // 未知
    int GENDER_UNKNOWN = 0;
    /**
     * 男
     */
    int GENDER_MAN = 1;
    /**
     * 女
     */
    int GENDER_WOMAN = 2;





    /**
     * 密码长度
     */
    int PASSWORD_LENGTH = 6;

    /**
     * 手机验证码过期时间
     */
    int PHONE_VERIFICATION_EXPIRATION_TIME = 300;
}
