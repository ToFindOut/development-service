package cn.com.partical.development.system.developmentservice.service.project.impl;

import cn.com.partical.development.system.developmentservice.dto.project.ProjectSearchResultDTO;
import cn.com.partical.development.system.developmentservice.dto.project.ProjectUpdateDTO;
import cn.com.partical.development.system.developmentservice.entity.ProjectInfo;
import cn.com.partical.development.system.developmentservice.mapper.project.IProjectMapper;
import cn.com.partical.development.system.developmentservice.service.project.IProjectService;
import cn.com.partical.development.system.developmentservice.util.filed.ActiveFlagEnum;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 11:41
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<IProjectMapper, ProjectInfo> implements IProjectService {

    @Autowired
    private IProjectMapper projectMapper;

    @Override
    public List<ProjectSearchResultDTO> searchProjectInfoByTeamId(Long teamId, String projectName) {

        List<ProjectSearchResultDTO> projectSearchResultDTOList = new LinkedList<>();

        QueryWrapper<ProjectInfo> projectInfoQueryWrapper = new QueryWrapper<>();
        projectInfoQueryWrapper.eq("team_id", teamId).eq("is_delete", ActiveFlagEnum.DEFAULT.getValue());

        if (StrUtil.isNotBlank(projectName)) {
            projectInfoQueryWrapper.eq("project_name", projectName);
        }

        List<ProjectInfo> projectInfoList = projectMapper.selectList(projectInfoQueryWrapper);

        projectInfoList.forEach(n ->{
            ProjectSearchResultDTO projectSearchResultDTO = new ProjectSearchResultDTO();
            BeanUtil.copyProperties(n, projectSearchResultDTO);

            projectSearchResultDTOList.add(projectSearchResultDTO);
        });

        return projectSearchResultDTOList;
    }

    @Override
    public IPage<ProjectUpdateDTO> listProjectInfo(Long userId, String projectName, Integer pageIndex, Integer pageSize) {
        Page<Object> page = new Page<>(pageIndex,pageSize);
        return projectMapper.listProjectInfo(page, userId, projectName);
    }

    @Override
    public boolean deleteProjectInfoById(Long projectId) {
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setIsDelete(ActiveFlagEnum.DELETE.getValue());

        UpdateWrapper<ProjectInfo> projectInfoUpdateWrapper = new UpdateWrapper<>();
        projectInfoUpdateWrapper.eq("id", projectId).eq("is_delete", ActiveFlagEnum.DEFAULT.getValue());

        return projectMapper.update(projectInfo, projectInfoUpdateWrapper) > 0;
    }

}
