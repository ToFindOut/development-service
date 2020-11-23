package cn.com.partical.development.system.developmentservice.service.project.impl;

import cn.com.partical.development.system.developmentservice.entity.ProjectMember;
import cn.com.partical.development.system.developmentservice.mapper.project.IProjectMemberMapper;
import cn.com.partical.development.system.developmentservice.service.project.IProjectMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 13:26
 */
@Service
public class ProjectMemberServiceImpl implements IProjectMemberService {

    @Autowired
    private IProjectMemberMapper projectMemberMapper;

    @Override
    public boolean saveProjectMemberInfo(List<ProjectMember> projectMemberList) {
        return projectMemberMapper.saveBatch(projectMemberList);
    }
}
