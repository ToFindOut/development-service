package cn.com.partical.development.system.developmentservice.service.user;

import cn.com.partical.development.system.developmentservice.dto.user.UserBaseInfoDTO;
import cn.com.partical.development.system.developmentservice.dto.user.UserLoginDTO;
import cn.com.partical.development.system.developmentservice.entity.UserInfo;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/18 9:42
 */
public interface IUserService {

    /**
     * 登录
     * @param userLoginDTO 参数
     * @return 状态
     */
    boolean login(UserLoginDTO userLoginDTO);

    /**
     * 查询用户基本信息
     * @param phone 手机号
     * @return 基本信息
     */
    UserBaseInfoDTO findUserBaseInfoByPhone(String phone);

    /**
     * 统计手机号是否存在
     * @param phone 手机号
     * @return 状态
     */
    boolean countUserPhoneExists(String phone);

    /**
     * 注册用户信息
     * @param userInfo 用户信息
     * @return 状态
     */
    boolean registerUserInfo(UserInfo userInfo);

    /**
     * 检查用户密码是否正确
     * @param userId 用户Id
     * @param pwd 密码
     * @return 状态
     */
    boolean checkUserPwdCorrect(long userId, String pwd);

    /**
     * 修改密码
     * @param userId 用户ID
     * @param pwd 密码
     * @return 状态
     */
    boolean updateUserPwd(long userId, String pwd);
}