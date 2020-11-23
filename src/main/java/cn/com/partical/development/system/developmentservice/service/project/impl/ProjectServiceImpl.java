package cn.com.partical.development.system.developmentservice.service.project.impl;

import cn.com.partical.development.system.developmentservice.dto.project.ProjectSearchResultDTO;
import cn.com.partical.development.system.developmentservice.entity.ProjectInfo;
import cn.com.partical.development.system.developmentservice.entity.ProjectMember;
import cn.com.partical.development.system.developmentservice.mapper.project.IProjectMapper;
import cn.com.partical.development.system.developmentservice.service.project.IProjectService;
import cn.com.partical.development.system.developmentservice.util.filed.ActiveFlagEnum;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private IProjectMapper projectMapper;

    @Override
    public List<ProjectSearchResultDTO> searchProjectInfoByTeamId(Long teamId, String projectName) {

        List<ProjectSearchResultDTO> projectSearchResultDTOList = new LinkedList<>();

        QueryWrapper<ProjectInfo> projectInfoQueryWrapper = new QueryWrapper<>();
        projectInfoQueryWrapper.eq("team_id", teamId).eq("active_flag", ActiveFlagEnum.DEFAULT.getValue());

        if (StrUtil.isNotBlank(projectName)) {
            projectInfoQueryWrapper.eq("project_name", projectName);
        }

        List<ProjectInfo> projectInfoList = projectMapper.selectList(projectInfoQueryWrapper);

        BeanUtil.copyProperties(projectInfoList, projectSearchResultDTOList);

        return projectSearchResultDTOList;
    }

}
