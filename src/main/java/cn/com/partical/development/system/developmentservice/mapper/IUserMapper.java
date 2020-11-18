package cn.com.partical.development.system.developmentservice.mapper;

import cn.com.partical.development.system.developmentservice.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMapper extends BaseMapper<UserInfo> {
}
