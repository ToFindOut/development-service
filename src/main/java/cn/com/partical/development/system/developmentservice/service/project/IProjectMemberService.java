package cn.com.partical.development.system.developmentservice.service.project;

import cn.com.partical.development.system.developmentservice.dto.project.ProjectMemberDTO;
import cn.com.partical.development.system.developmentservice.entity.ProjectMember;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 13:26
 */
public interface IProjectMemberService extends IService<ProjectMember> {

    /**
     * 检查用户是否是项目创建者
     * @param userId 用户ID
     * @param projectId 项目ID
     * @return boolean
     */
    boolean checkUserIsProjectMemberCreator(Long userId, Long projectId);

    /**
     * 检查用户是否是项目管理员
     * @param userId 用户ID
     * @param projectId 项目ID
     * @return boolean
     */
    boolean checkUserIsProjectMemberAdministrator(Long userId, Long projectId);

    /**
     * 检查用户是否是项目编辑员
     * @param userId 用户ID
     * @param projectId 项目ID
     * @return boolean
     */
    boolean checkUserIsProjectMemberEditor(Long userId, Long projectId);

    /**
     * 检查用户是否是项目创建者或管理员
     * @param userId 用户ID
     * @param projectId 项目ID
     * @return boolean
     */
    boolean checkUserIsProjectMemberCreatorOrAdministrator(Long userId, Long projectId);

    /**
     * 删除项目成员信息
     * @param projectId 项目ID
     * @return boolean
     */
    boolean deleteProjectMemberInfoByProjectId(Long projectId);

    /**
     * 修改项目成员信息
     * @param userId 用户ID
     * @param projectId 项目ID
     * @param projectMemberType 项目成员类型 1：管理员，2：编辑员，3：阅读员
     * @return boolean
     */
    boolean updateProjectMemberIdentityInfo(Long userId, Long projectId, Integer projectMemberType);

    /**
     * 移除项目成员
     * @param userId 用户ID
     * @param projectId 项目ID
     * @return boolean
     */
    boolean removeProjectMember(Long userId, Long projectId);

    /**
     * 项目标注
     * @param userId 项目成员用户ID
     * @param projectId 项目ID
     * @return boolean
     */
    boolean markProject(Long userId, Long projectId);

    /**
     * 查询项目成员信息
     * @param projectId 项目ID
     * @param pageIndex 页码
     * @param pageSize 每页显示记录条数
     * @return 项目成员信息
     */
    IPage<ProjectMemberDTO> listProjectMemberInfo(Long projectId, Integer pageIndex, Integer pageSize);
}
