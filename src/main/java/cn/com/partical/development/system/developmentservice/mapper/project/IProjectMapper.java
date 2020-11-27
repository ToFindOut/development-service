package cn.com.partical.development.system.developmentservice.mapper.project;

import cn.com.partical.development.system.developmentservice.dto.project.ProjectUpdateDTO;
import cn.com.partical.development.system.developmentservice.entity.ProjectInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 11:40
 */
@Mapper
public interface IProjectMapper extends BaseMapper<ProjectInfo> {

    /**
     * 查询项目列表
     * @param page 分页参数
     * @param userId 用户ID
     * @param projectName 项目名称
     * @return 项目信息
     */
    @Select("SELECT\n" +
            "    a.id AS projectId, \n" +
            "    a.project_name AS projectName, \n" +
            "    a.project_introduce AS projectIntroduce, \n" +
            "    a.open_type AS openType, \n" +
            "    a.project_state AS projectState \n" +
            "FROM\n" +
            "    project_info a\n" +
            "    LEFT JOIN project_member b ON a.id = b.project_id \n" +
            "WHERE\n" +
            "    a.project_name LIKE CONCAT('%',#{proejctName},'%') \n" +
            "    AND a.is_delete = 0 \n" +
            "    AND b.is_delete = 0 \n" +
            "    AND b.user_id = #{userId}\n" +
            "ORDER BY\n" +
            "    a.create_time DESC")
    IPage<ProjectUpdateDTO> listProjectInfo(Page<Object> page,
                                            @Param("userId") Long userId,
                                            @Param("projectName") String projectName);
}
