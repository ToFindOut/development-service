package cn.com.partical.development.system.developmentservice.mapper.project;

import cn.com.partical.development.system.developmentservice.entity.ProjectMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 13:27
 */
@Mapper
public interface IProjectMemberMapper extends IService<ProjectMember> {
}
