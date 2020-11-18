package cn.com.partical.development.system.developmentservice.controller.user;

import cn.com.partical.development.system.developmentservice.base.BaseController;
import cn.com.partical.development.system.developmentservice.common.constant.UserConstant;
import cn.com.partical.development.system.developmentservice.common.global.GlobalApiResponse;
import cn.com.partical.development.system.developmentservice.common.global.GlobalException;
import cn.com.partical.development.system.developmentservice.dto.user.*;
import cn.com.partical.development.system.developmentservice.entity.UserInfo;
import cn.com.partical.development.system.developmentservice.service.user.IUserService;
import cn.com.partical.development.system.developmentservice.util.api.ResponseUtil;
import cn.com.partical.development.system.developmentservice.util.cache.RedisUtil;
import cn.com.partical.development.system.developmentservice.util.security.SendSmsVerificationCode;
import cn.hutool.core.util.*;
import cn.hutool.crypto.SecureUtil;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wang
 * @date 2020/11/17 14:55
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块管理")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${setting.sms.switch}")
    private String SMS_SWITCH;

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public GlobalApiResponse<String> login(@RequestBody UserLoginDTO userLoginDTO) {

        if (userLoginDTO == null || StrUtil.hasBlank(userLoginDTO.getPhone(),userLoginDTO.getPwd())) {
            return ResponseUtil.error(403, "参数不能为空");
        }

        if (!PhoneUtil.isPhone(userLoginDTO.getPhone())) {
            return ResponseUtil.error(403, "手机号格式错误");
        }

        if (userService.login(userLoginDTO)) {
            // 通过手机号查询用户基本信息
            UserBaseInfoDTO userBaseInfo = userService.findUserBaseInfoByPhone(userLoginDTO.getPhone());

            String token = UserConstant.LOGIN_TOKEN_PREFIX+userBaseInfo.getPhone()+UserConstant.UNDERLINE+IdUtil.simpleUUID();

            userBaseInfo.setToken(token);

            // 删除之前登录TOKEN
            redisUtil.deleteByPrex(UserConstant.LOGIN_TOKEN_PREFIX+userBaseInfo.getPhone()+UserConstant.FUZZY_SYMBOL);

            // 封装token
            redisUtil.setValue(token, userBaseInfo.getId(), 86400*5);

            return ResponseUtil.success("登录成功", userBaseInfo);
        }

        return ResponseUtil.error(501,"登录失败，手机号或密码错误");
    }

    @ApiOperation(value = "发送验证码（1 注册 2 忘记密码 ）")
    @RequestMapping(value = "/send/code/{type}/{phone}", method = RequestMethod.GET)
    public GlobalApiResponse<String> sendCode(@PathVariable int type, @PathVariable String phone) {
        if (!PhoneUtil.isPhone(phone)) {
            return ResponseUtil.error(403, "手机号格式错误");
        }

        boolean phoneFlag = userService.countUserPhoneExists(phone);

        if (type == UserConstant.VERIFICATION_REGISTER_CODE) {
            // 验证手机号是否存在
            if (phoneFlag) {
                throw new GlobalException(501, "手机号已存在");
            }
        } else {
            if (!phoneFlag) {
                throw new GlobalException(403, "手机号不存在");
            }
        }
        String code;
        if ("true".equals(SMS_SWITCH)) {
            code = SendSmsVerificationCode.sendSmsVerificationCodeViaPhoneNumber(phone);
        } else {
            code = RandomStringUtils.randomNumeric(4);
        }

        if (type == UserConstant.VERIFICATION_REGISTER_CODE) {
            // 存入redis 一分钟过期时间
            redisUtil.setValue(UserConstant.PHONE_VERIFICATION_PREFIX+phone, code, UserConstant.PHONE_VERIFICATION_EXPIRATION_TIME);
        }

        return ResponseUtil.success("发送成功");
    }


    @ApiOperation(value = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public GlobalApiResponse<String> register(@RequestBody UserRegisterInfoDTO userRegisterInfoDTO) {

        if (userRegisterInfoDTO == null || StrUtil.hasBlank(userRegisterInfoDTO.getPhone(),userRegisterInfoDTO.getPwd(),userRegisterInfoDTO.getCode())) {
            return ResponseUtil.error(403, "参数不能为空");
        }

        if (!PhoneUtil.isPhone(userRegisterInfoDTO.getPhone())) {
            return ResponseUtil.error(403, "手机号格式错误");
        }

        if (userRegisterInfoDTO.getPwd().length() < UserConstant.PASSWORD_LENGTH) {
            return ResponseUtil.error(405, "密码长度最小"+UserConstant.PASSWORD_LENGTH+"位");
        }

        if (userService.countUserPhoneExists(userRegisterInfoDTO.getPhone())) {
            throw new GlobalException(501, "该手机号已存在");
        }

        if (redisUtil.getValue(UserConstant.PHONE_VERIFICATION_PREFIX+userRegisterInfoDTO.getPhone()) == null) {
            return ResponseUtil.error(403, "验证码已过期,请重新发送");
        }

        if (!userRegisterInfoDTO.getCode().equals(redisUtil.getValue(UserConstant.PHONE_VERIFICATION_PREFIX+userRegisterInfoDTO.getPhone()))) {
            return ResponseUtil.error(403, "验证码错误");
        }

        UserInfo userInfo = new UserInfo();

        userInfo.setPhone(userRegisterInfoDTO.getPhone());
        userInfo.setPwd(SecureUtil.md5(userRegisterInfoDTO.getPwd()));

        if (userService.registerUserInfo(userInfo)) {
            return ResponseUtil.success("注册成功");
        }

        return ResponseUtil.error(405, "注册失败,请重试");
    }

    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/change/pwd", method = RequestMethod.POST)
    public GlobalApiResponse<String> changePwd(HttpServletRequest request,
                                               @RequestBody UserChangePwdDTO userChangePwdDTO) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        if (userChangePwdDTO == null || StrUtil.hasBlank(userChangePwdDTO.getOldPwd(),userChangePwdDTO.getReNewPwd(),userChangePwdDTO.getNewPwd())) {
            return ResponseUtil.error(403, "参数不能为空");
        }

        if (userChangePwdDTO.getNewPwd().length() < UserConstant.PASSWORD_LENGTH) {
            return ResponseUtil.error(405, "密码长度最小"+UserConstant.PASSWORD_LENGTH+"位");
        }

        if (!userChangePwdDTO.getNewPwd().equals(userChangePwdDTO.getReNewPwd())) {
            return ResponseUtil.error(405, "两次密码不一致");
        }

        // 检查用户旧密码是否正确
        if (!userService.checkUserPwdCorrect(userId, userChangePwdDTO.getOldPwd())) {
            return ResponseUtil.error(405, "原密码错误");
        }

        if (userService.updateUserPwd(userId, userChangePwdDTO.getNewPwd())) {
            return ResponseUtil.success("修改成功");
        }

        return ResponseUtil.error(405, "修改失败");
    }

    @ApiOperation(value = "找回密码")
    @RequestMapping(value = "/back/pwd", method = RequestMethod.POST)
    public GlobalApiResponse<String> backUserPwd(@RequestBody UserBackPwdDTO userBackPwdDTO) {


        return ResponseUtil.success();
    }
}
