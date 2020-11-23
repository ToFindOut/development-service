package cn.com.partical.development.system.developmentservice.service.user.impl;

import cn.com.partical.development.system.developmentservice.dto.user.UserBaseInfoDTO;
import cn.com.partical.development.system.developmentservice.dto.user.UserLoginDTO;
import cn.com.partical.development.system.developmentservice.dto.user.UserSearchBaseDTO;
import cn.com.partical.development.system.developmentservice.dto.user.UserSearchDTO;
import cn.com.partical.development.system.developmentservice.entity.UserInfo;
import cn.com.partical.development.system.developmentservice.mapper.user.IUserMapper;
import cn.com.partical.development.system.developmentservice.service.user.IUserService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/18 9:44
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserMapper userMapper;

    @Override
    public boolean login(UserLoginDTO userLoginDTO) {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("phone", userLoginDTO.getPhone()).eq("pwd", SecureUtil.md5(userLoginDTO.getPwd()));
        return userMapper.selectCount(userInfoQueryWrapper) > 0 ;
    }

    @Override
    public UserBaseInfoDTO findUserBaseInfoByPhone(String phone) {

        UserBaseInfoDTO userBaseInfoDTO = new UserBaseInfoDTO();

        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("phone", phone);

        UserInfo userBaseInfo = userMapper.selectOne(userInfoQueryWrapper);

        BeanUtil.copyProperties(userBaseInfo,userBaseInfoDTO);

        return userBaseInfoDTO;
    }

    @Override
    public boolean registerUserInfo(UserInfo userInfo) {
        return userMapper.insert(userInfo) > 0;
    }

    @Override
    public boolean checkUserPwdCorrect(long userId, String pwd) {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("id", userId);
        userInfoQueryWrapper.eq("pwd", SecureUtil.md5(pwd));

        return userMapper.selectCount(userInfoQueryWrapper) > 0;
    }

    @Override
    public boolean updateUserPwd(long userId, String pwd) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setPwd(SecureUtil.md5(pwd));

        return userMapper.updateById(userInfo) > 0;
    }

    @Override
    public long findUserIdByPhone(String phone) {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("phone", phone);

        return userMapper.selectOne(userInfoQueryWrapper).getId();
    }

    @Override
    public boolean checkPhoneWhetherOrNotUsed(String phone, Long userId) {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("phone", phone);

        // 如果用户Id不为空则排除本身
        if(userId != null) {
            userInfoQueryWrapper.ne("id", userId);
        }

        return userMapper.selectCount(userInfoQueryWrapper) > 0;
    }

    @Override
    public boolean updateUserInfo(UserInfo userInfo) {
        return userMapper.updateById(userInfo) > 0;
    }

    @Override
    public UserSearchBaseDTO searchUserInfo(UserSearchDTO userSearchDTO) {

        UserSearchBaseDTO userSearchBaseDTO = new UserSearchBaseDTO();

        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("id", userSearchDTO.getId()).or().eq("phone", userSearchDTO.getPhone());

        UserInfo userInfo = userMapper.selectOne(userInfoQueryWrapper);

        BeanUtil.copyProperties(userInfo, userSearchBaseDTO);
        return userSearchBaseDTO;
    }
}
