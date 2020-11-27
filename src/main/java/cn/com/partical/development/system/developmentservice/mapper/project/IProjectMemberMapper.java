package cn.com.partical.development.system.developmentservice.mapper.project;

import cn.com.partical.development.system.developmentservice.entity.ProjectMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 13:27
 */
@Mapper
public interface IProjectMemberMapper extends BaseMapper<ProjectMember> {
    /**
     * 项目标注
     * @param userId 项目成员用户ID
     * @param projectId 项目ID
     * @return boolean
     */
    @Update("UPDATE project_member SET flag = CASE WHEN flag = 1 THEN 0 ELSE 1 END WHERE user_id = #{userId} AND project_id = #{projectId}  AND is_delete = 0")
    boolean markProject(@Param("userId") Long userId,@Param("projectId") Long projectId);
}
