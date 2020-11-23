package cn.com.partical.development.system.developmentservice.service.project;

import cn.com.partical.development.system.developmentservice.dto.project.ProjectSearchResultDTO;
import cn.com.partical.development.system.developmentservice.entity.ProjectMember;

import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 11:40
 */
public interface IProjectService {

    /**
     * 根据团队Id搜索项目信息
     * @param teamId 团队Id
     * @param projectName 项目名称
     * @return 搜索结果
     */
    List<ProjectSearchResultDTO> searchProjectInfoByTeamId(Long teamId, String projectName);
}
