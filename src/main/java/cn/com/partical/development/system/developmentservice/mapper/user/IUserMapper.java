package cn.com.partical.development.system.developmentservice.mapper.user;

import cn.com.partical.development.system.developmentservice.dto.user.UserSearchBaseDTO;
import cn.com.partical.development.system.developmentservice.dto.user.UserSearchDTO;
import cn.com.partical.development.system.developmentservice.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@SuppressWarnings("ALL")
@Mapper
public interface IUserMapper extends BaseMapper<UserInfo> {
}
