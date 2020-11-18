package cn.com.partical.development.system.developmentservice.service.user.impl;

import cn.com.partical.development.system.developmentservice.dto.user.UserBaseInfoDTO;
import cn.com.partical.development.system.developmentservice.dto.user.UserLoginDTO;
import cn.com.partical.development.system.developmentservice.entity.UserInfo;
import cn.com.partical.development.system.developmentservice.mapper.IUserMapper;
import cn.com.partical.development.system.developmentservice.service.user.IUserService;
import cn.hutool.core.bean.BeanInfoCache;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.DynaBean;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
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
    public boolean countUserPhoneExists(String phone) {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("phone", phone);
        return userMapper.selectCount(userInfoQueryWrapper) > 0;
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
}
