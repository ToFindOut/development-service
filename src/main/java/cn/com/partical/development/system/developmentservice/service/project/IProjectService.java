package cn.com.partical.development.system.developmentservice.service.project;

import cn.com.partical.development.system.developmentservice.dto.project.ProjectSearchResultDTO;
import cn.com.partical.development.system.developmentservice.dto.project.ProjectUpdateDTO;
import cn.com.partical.development.system.developmentservice.entity.ProjectInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 11:40
 */
public interface IProjectService extends IService<ProjectInfo> {

    /**
     * 根据团队Id搜索项目信息
     * @param teamId 团队Id
     * @param projectName 项目名称
     * @return 搜索结果
     */
    List<ProjectSearchResultDTO> searchProjectInfoByTeamId(Long teamId, String projectName);

    /**
     * 查询项目信息
     * @param userId 用户编号
     * @param projectName 项目名称
     * @param pageIndex 页码
     * @param pageSize 每页显示记录条数
     * @return 项目信息
     */
    IPage<ProjectUpdateDTO> listProjectInfo(Long userId, String projectName, Integer pageIndex, Integer pageSize);

    /**
     * 删除项目信息
     * @param projectId 项目ID
     * @return boolean
     */
    boolean deleteProjectInfoById(Long projectId);
}
