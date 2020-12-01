package cn.com.partical.development.system.developmentservice.controller;

import cn.com.partical.development.system.developmentservice.base.BaseController;
import cn.com.partical.development.system.developmentservice.common.constant.IParamConstant;
import cn.com.partical.development.system.developmentservice.common.constant.IUserConstant;
import cn.com.partical.development.system.developmentservice.common.global.GlobalApiResponse;
import cn.com.partical.development.system.developmentservice.common.global.GlobalException;
import cn.com.partical.development.system.developmentservice.dto.user.*;
import cn.com.partical.development.system.developmentservice.entity.TeamInfo;
import cn.com.partical.development.system.developmentservice.entity.TeamMember;
import cn.com.partical.development.system.developmentservice.entity.UserInfo;
import cn.com.partical.development.system.developmentservice.service.team.ITeamMemberService;
import cn.com.partical.development.system.developmentservice.service.team.ITeamService;
import cn.com.partical.development.system.developmentservice.service.user.IUserService;
import cn.com.partical.development.system.developmentservice.util.api.ResponseUtil;
import cn.com.partical.development.system.developmentservice.util.cache.RedisUtil;
import cn.com.partical.development.system.developmentservice.util.security.SendSmsVerificationCode;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.*;
import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    private String notificationSwitch;
    
    @Autowired
    private ITeamService teamService;
    
    @Autowired
    private ITeamMemberService teamMemberService;

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public GlobalApiResponse<UserBaseInfoDTO> login(@RequestBody UserLoginDTO userLoginDTO) {

        if (userLoginDTO == null || StrUtil.hasBlank(userLoginDTO.getPhone(),userLoginDTO.getPwd())) {
            return ResponseUtil.error(403, "参数不能为空");
        }

        if (!PhoneUtil.isPhone(userLoginDTO.getPhone())) {
            return ResponseUtil.error(403, "手机号格式错误");
        }

        if (userService.login(userLoginDTO)) {
            // 通过手机号查询用户基本信息
            UserBaseInfoDTO userBaseInfo = userService.findUserBaseInfoByPhone(userLoginDTO.getPhone());

            String token = IUserConstant.LOGIN_TOKEN_PREFIX+userBaseInfo.getPhone()+IParamConstant.UNDERLINE+IdUtil.simpleUUID();

            Map<String,Object> build = MapUtil.newHashMap();
            build.put("token", token);
            build.put("userInfo", userBaseInfo);


            // 删除之前登录TOKEN
            redisUtil.deleteByPrex(IUserConstant.LOGIN_TOKEN_PREFIX+userBaseInfo.getPhone()+IParamConstant.FUZZY_SYMBOL);

            // 封装token
            redisUtil.setValue(token, userBaseInfo.getId(), 86400*5);

            return ResponseUtil.success("登录成功", build);
        }

        return ResponseUtil.error(501,"登录失败，手机号或密码错误");
    }

    @ApiOperation(value = "发送验证码（1 注册 2 忘记密码 3 修改手机号）")
    @RequestMapping(value = "/send/code/{type}/{phone}", method = RequestMethod.GET)
    public GlobalApiResponse<String> sendCode(HttpServletRequest request,
                                              @PathVariable int type,
                                              @PathVariable String phone) {
        Long userId = super.getUserId(request);

        if (!PhoneUtil.isPhone(phone)) {
            return ResponseUtil.error(403, "手机号格式错误");
        }

        boolean phoneFlag = userService.checkPhoneWhetherOrNotUsed(phone, userId);

        switch (type) {
            case IUserConstant.VERIFICATION_REGISTER_CODE :
                // 验证手机号是否存在
                if (phoneFlag) {
                    return ResponseUtil.error(501, "手机号已存在");
                }
                break;
            case IUserConstant.VERIFICATION_RETRIEVE_PASSWORD_CODE :
                if (!phoneFlag) {
                    return ResponseUtil.error(405, "手机号不存在");
                }
                break;
            case IUserConstant.UPDATE_PHONE_VERIFICATION_CODE :
                if (userId == null) {
                    return ResponseUtil.error(401, "用户身份已过期");
                }
                // 验证手机号是否存在
                if (phoneFlag) {
                    return ResponseUtil.error(501, "手机号已存在");
                }
                break;
            default:
                return ResponseUtil.error(403, "类型错误");
        }

        String code;
        // 发送短信验证码开关 true 开启 false 关闭
        if (IParamConstant.SWITCH_STATE.equals(notificationSwitch)) {
            code = SendSmsVerificationCode.sendSmsVerificationCodeViaPhoneNumber(phone);
        } else {
            code = RandomStringUtils.randomNumeric(4);
        }


        switch (type) {
            case IUserConstant.VERIFICATION_REGISTER_CODE :
                // 注册
                redisUtil.setValue(IUserConstant.REGISTER_PHONE_VERIFICATION_PREFIX+phone, code, IUserConstant.PHONE_VERIFICATION_EXPIRATION_TIME);
                break;
            case IUserConstant.VERIFICATION_RETRIEVE_PASSWORD_CODE :
                // 找回密码
                redisUtil.setValue(IUserConstant.RETRIEVE_PASSWORD_VERIFICATION_PREFIX+phone, code, IUserConstant.PHONE_VERIFICATION_EXPIRATION_TIME);
                break;
            case IUserConstant.UPDATE_PHONE_VERIFICATION_CODE :
                // 修改手机号
                redisUtil.setValue(IUserConstant.UPDATE_PHONE_VERIFICATION_PREFIX+phone, code, IUserConstant.PHONE_VERIFICATION_EXPIRATION_TIME);
                break;
            default:
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

        if (userRegisterInfoDTO.getPwd().length() < IUserConstant.PASSWORD_LENGTH) {
            return ResponseUtil.error(405, "密码长度最小"+IUserConstant.PASSWORD_LENGTH+"位");
        }

        if (userService.checkPhoneWhetherOrNotUsed(userRegisterInfoDTO.getPhone(), null)) {
            throw new GlobalException(501, "该手机号已存在");
        }

        if (redisUtil.getValue(IUserConstant.REGISTER_PHONE_VERIFICATION_PREFIX+userRegisterInfoDTO.getPhone()) == null) {
            return ResponseUtil.error(403, "验证码已过期,请重新发送");
        }

        if (!userRegisterInfoDTO.getCode().equals(redisUtil.getValue(IUserConstant.REGISTER_PHONE_VERIFICATION_PREFIX+userRegisterInfoDTO.getPhone()))) {
            return ResponseUtil.error(403, "验证码错误");
        }

        UserInfo userInfo = new UserInfo();

        userInfo.setPhone(userRegisterInfoDTO.getPhone());
        userInfo.setPwd(SecureUtil.md5(userRegisterInfoDTO.getPwd()));

        if (userService.registerUserInfo(userInfo)) {
            TeamInfo teamInfo = new TeamInfo();
            teamInfo.setTeamName(userRegisterInfoDTO.getPhone());
            teamService.save(teamInfo);

            TeamMember teamMember = new TeamMember();
            teamMember.setTeamId(teamInfo.getId());
            teamMember.setUserId(userInfo.getId());
            teamMemberService.save(teamMember);
           
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

        if (userChangePwdDTO.getNewPwd().length() < IUserConstant.PASSWORD_LENGTH) {
            return ResponseUtil.error(405, "密码长度最小"+IUserConstant.PASSWORD_LENGTH+"位");
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

        if (userBackPwdDTO == null || StrUtil.hasBlank(userBackPwdDTO.getPhone(), userBackPwdDTO.getNewPwd(), userBackPwdDTO.getReNewPwd(), userBackPwdDTO.getCode())) {
            return ResponseUtil.error(403, "参数不能为空");
        }

        if (!PhoneUtil.isPhone(userBackPwdDTO.getPhone())) {
            return ResponseUtil.error(403, "手机号格式错误");
        }

        if (userBackPwdDTO.getNewPwd().length() < IUserConstant.PASSWORD_LENGTH) {
            return ResponseUtil.error(405, "密码长度最小"+IUserConstant.PASSWORD_LENGTH+"位");
        }

        if (!userBackPwdDTO.getNewPwd().equals(userBackPwdDTO.getReNewPwd())) {
            return ResponseUtil.error(405, "两次密码不一致");
        }


        if (redisUtil.getValue(IUserConstant.RETRIEVE_PASSWORD_VERIFICATION_PREFIX+userBackPwdDTO.getPhone()) == null) {
            return ResponseUtil.error(403, "验证码已过期,请重新发送");
        }

        if (!userBackPwdDTO.getCode().equals(redisUtil.getValue(IUserConstant.RETRIEVE_PASSWORD_VERIFICATION_PREFIX+userBackPwdDTO.getPhone()))) {
            return ResponseUtil.error(403, "验证码错误");
        }

        if (userService.updateUserPwd(userService.findUserIdByPhone(userBackPwdDTO.getPhone()), userBackPwdDTO.getNewPwd())) {
            return ResponseUtil.success("修改成功");
        }


        return ResponseUtil.error("失败,请重试");
    }

    @ApiOperation(value = "修改个人信息")
    @RequestMapping(value = "/change/information", method = RequestMethod.POST)
    public GlobalApiResponse<String> changeInformation(HttpServletRequest request,
                                                       @RequestBody UserBaseInfoDTO userBaseInfoDTO) {

        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        if (userBaseInfoDTO == null) {
            return ResponseUtil.error(403, "参数不能为空");
        }

        // 手机号
        if (StrUtil.isNotBlank(userBaseInfoDTO.getPhone())) {

            if (!PhoneUtil.isPhone(userBaseInfoDTO.getPhone())) {
                return ResponseUtil.error(403, "手机号格式错误");
            }

            if (StrUtil.isBlank(userBaseInfoDTO.getCode())) {
                return ResponseUtil.error(406, "请输入验证码");
            }

            if (redisUtil.getValue(IUserConstant.UPDATE_PHONE_VERIFICATION_PREFIX+userBaseInfoDTO.getPhone()) == null) {
                return ResponseUtil.error(403, "验证码已过期,请重新发送");
            }

            if (!userBaseInfoDTO.getCode().equals(redisUtil.getValue(IUserConstant.UPDATE_PHONE_VERIFICATION_PREFIX+userBaseInfoDTO.getPhone()))) {
                return ResponseUtil.error(403, "验证码错误");
            }

        }

        // 性别
        if (userBaseInfoDTO.getGender() != null && userBaseInfoDTO.getGender() != IUserConstant.GENDER_UNKNOWN
                && userBaseInfoDTO.getGender() != IUserConstant.GENDER_MAN && userBaseInfoDTO.getGender() != IUserConstant.GENDER_WOMAN) {
            return ResponseUtil.error(403, "参数类型错误");
        }


        // 身份证
        if (StrUtil.isNotBlank(userBaseInfoDTO.getIdentityCard())) {
            if (!IdcardUtil.isValidCard(userBaseInfoDTO.getIdentityCard())) {
                return ResponseUtil.error(502, "身份证不合法");
            }
        }

        UserInfo userInfo = new UserInfo();

        BeanUtil.copyProperties(userBaseInfoDTO, userInfo);

        userInfo.setId(userId);
        if (userService.updateUserInfo(userInfo)) {
            return ResponseUtil.success("修改成功");
        }

        return ResponseUtil.error("修改失败");
    }


    @ApiOperation(value = "搜索成员")
    @RequestMapping(value = "/search/member/{param}", method = RequestMethod.GET)
    public GlobalApiResponse<UserSearchBaseDTO> searchMember(HttpServletRequest request,
                                                  @PathVariable String param) {

        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        if (StrUtil.isBlank(param)) {
            return ResponseUtil.error(403, "搜索参数不能为空");
        }

        Integer type = 2;

        // 校验搜索参数是否是手机号
        if (PhoneUtil.isPhone(param)) {
            type = 1;
        } else {
            if (param.equals(userId.toString())) {
                return ResponseUtil.error(405, "搜索人不能是自己本身");
            }
        }

        return ResponseUtil.success(userService.searchUserInfo(param, type));
    }
}
