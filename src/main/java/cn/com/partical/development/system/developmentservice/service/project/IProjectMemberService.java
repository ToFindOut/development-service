package cn.com.partical.development.system.developmentservice.service.project;

import cn.com.partical.development.system.developmentservice.entity.ProjectMember;

import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 13:26
 */
public interface IProjectMemberService {
    /**
     * 新增项目成员信息
     * @param projectMemberList 项目成员信息
     * @return 新增状态
     */
    boolean saveProjectMemberInfo(List<ProjectMember> projectMemberList);
}
