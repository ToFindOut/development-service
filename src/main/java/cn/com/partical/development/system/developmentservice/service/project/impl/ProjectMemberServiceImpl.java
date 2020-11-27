package cn.com.partical.development.system.developmentservice.service.project.impl;

import cn.com.partical.development.system.developmentservice.common.constant.IProjectConstant;
import cn.com.partical.development.system.developmentservice.entity.ProjectMember;
import cn.com.partical.development.system.developmentservice.mapper.project.IProjectMemberMapper;
import cn.com.partical.development.system.developmentservice.service.project.IProjectMemberService;
import cn.com.partical.development.system.developmentservice.util.filed.ActiveFlagEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 13:26
 */
@Service
public class ProjectMemberServiceImpl extends ServiceImpl<IProjectMemberMapper, ProjectMember> implements IProjectMemberService {

    @Autowired
    private IProjectMemberMapper projectMemberMapper;

    @Override
    public boolean checkUserIsProjectMemberCreator(Long userId, Long projectId) {
        QueryWrapper<ProjectMember> projectMemberQueryWrapper = new QueryWrapper<>();
        projectMemberQueryWrapper.eq("user_id", userId).eq("project_id", projectId)
                .eq("project_member_type", IProjectConstant.PROJECT_MEMBER_TYPE_CREATE).eq("is_delete", ActiveFlagEnum.DEFAULT.getValue());

        return projectMemberMapper.selectCount(projectMemberQueryWrapper) < 1;
    }

    @Override
    public boolean checkUserIsProjectMemberAdministrator(Long userId, Long projectId) {
        QueryWrapper<ProjectMember> projectMemberQueryWrapper = new QueryWrapper<>();
        projectMemberQueryWrapper.eq("user_id", userId).eq("project_id", projectId)
                .eq("project_member_type", IProjectConstant.PROJECT_MEMBER_TYPE_ADMINISTRATOR).eq("is_delete", ActiveFlagEnum.DEFAULT.getValue());

        return projectMemberMapper.selectCount(projectMemberQueryWrapper) < 1;
    }

    @Override
    public boolean checkUserIsProjectMemberEditor(Long userId, Long projectId) {
        QueryWrapper<ProjectMember> projectMemberQueryWrapper = new QueryWrapper<>();
        projectMemberQueryWrapper.eq("user_id", userId).eq("project_id", projectId)
                .eq("project_member_type", IProjectConstant.PROJECT_MEMBER_TYPE_EDITOR).eq("is_delete", ActiveFlagEnum.DEFAULT.getValue());

        return projectMemberMapper.selectCount(projectMemberQueryWrapper) < 1;
    }

    @Override
    public boolean checkUserIsProjectMemberCreatorOrAdministrator(Long userId, Long projectId) {
        QueryWrapper<ProjectMember> projectMemberQueryWrapper = new QueryWrapper<>();
        projectMemberQueryWrapper.eq("user_id", userId).eq("project_id", projectId)
                .in("project_member_type", IProjectConstant.PROJECT_MEMBER_TYPE_CREATE, IProjectConstant.PROJECT_MEMBER_TYPE_ADMINISTRATOR)
                .eq("is_delete", ActiveFlagEnum.DEFAULT.getValue());

        return projectMemberMapper.selectCount(projectMemberQueryWrapper) < 1;
    }

    @Override
    public boolean deleteProjectMemberInfoByProjectId(Long projectId) {
        ProjectMember projectMember = new ProjectMember();
        projectMember.setIsDelete(ActiveFlagEnum.DELETE.getValue());

        UpdateWrapper<ProjectMember> projectMemberUpdateWrapper = new UpdateWrapper<>();
        projectMemberUpdateWrapper.eq("project_id", projectId).eq("is_delete", ActiveFlagEnum.DEFAULT.getValue());

        return projectMemberMapper.update(projectMember, projectMemberUpdateWrapper) > 0;
    }

    @Override
    public boolean updateProjectMemberIdentityInfo(Long userId, Long projectId, Integer projectMemberType) {
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProjectMemberType(projectMemberType);

        UpdateWrapper<ProjectMember> projectMemberUpdateWrapper = new UpdateWrapper<>();
        projectMemberUpdateWrapper.eq("project_id", projectId).eq("user_id", userId).eq("is_delete", ActiveFlagEnum.DEFAULT.getValue());

        return projectMemberMapper.update(projectMember, projectMemberUpdateWrapper) > 0;
    }

    @Override
    public boolean removeProjectMember(Long userId, Long projectId) {
        ProjectMember projectMember = new ProjectMember();
        projectMember.setIsDelete(ActiveFlagEnum.DELETE.getValue());

        UpdateWrapper<ProjectMember> projectMemberUpdateWrapper = new UpdateWrapper<>();
        projectMemberUpdateWrapper.eq("project_id", projectId).eq("user_id", userId).eq("is_delete", ActiveFlagEnum.DEFAULT.getValue());

        return projectMemberMapper.update(projectMember, projectMemberUpdateWrapper) > 0;
    }

    @Override
    public boolean markProject(Long userId, Long projectId) {
        return projectMemberMapper.markProject(userId, projectId);
    }
}
