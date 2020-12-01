package cn.com.partical.development.system.developmentservice.mapper.project;

import cn.com.partical.development.system.developmentservice.dto.project.ProjectMemberDTO;
import cn.com.partical.development.system.developmentservice.entity.ProjectMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    /**
     * 查询项目成员信息
     * @param projectId 项目ID
     * @param page 分页参数
     * @return 项目成员信息
     */
    @Select("SELECT\n" +
            "    a.user_id AS userId,\n" +
            "    b.user_nick_name AS userName,\n" +
            "    a.project_member_type AS projectMemberType\n" +
            "FROM\n" +
            "    project_member a\n" +
            "    LEFT JOIN ( SELECT id, user_nick_name FROM user_info ) b ON a.user_id = b.id \n" +
            "WHERE\n" +
            "    a.is_delete = 0 \n" +
            "    AND a.project_id = #{projectId}")
    IPage<ProjectMemberDTO> listProjectMemberInfo(Page<Object> page, @Param("projectId") Long projectId);
}
