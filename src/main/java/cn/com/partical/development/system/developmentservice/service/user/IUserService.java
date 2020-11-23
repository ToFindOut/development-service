package cn.com.partical.development.system.developmentservice.service.user;

import cn.com.partical.development.system.developmentservice.dto.user.UserBaseInfoDTO;
import cn.com.partical.development.system.developmentservice.dto.user.UserLoginDTO;
import cn.com.partical.development.system.developmentservice.dto.user.UserSearchBaseDTO;
import cn.com.partical.development.system.developmentservice.dto.user.UserSearchDTO;
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

    /**
     * 查询用户ID
     * @param phone 手机号
     * @return 用户Id
     */
    long findUserIdByPhone(String phone);

    /**
     * 检查手机号是否被使用
     * @param phone 手机号
     * @param userId 用户Id
     * @return 状态
     */
    boolean checkPhoneWhetherOrNotUsed(String phone, Long userId);

    /**
     * 修改用户信息
     * @param userInfo 用户信息
     * @return 状态
     */
    boolean updateUserInfo(UserInfo userInfo);

    /**
     * 搜索用户信息
     * @param userSearchDTO 用户搜索信息
     * @return 参数
     */
    UserSearchBaseDTO searchUserInfo(UserSearchDTO userSearchDTO);
}
