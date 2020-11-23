package cn.com.partical.development.system.developmentservice.service.team;

import cn.com.partical.development.system.developmentservice.dto.team.TeamMemberDTO;
import cn.com.partical.development.system.developmentservice.entity.TeamMember;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 10:12
 */
public interface ITeamMemberService {
    /**
     * 检查用户是否是团队管理者
     * @param userId 用户Id
     * @param teamId 团队Id
     * @return 状态
     */
    boolean checkUserIsTeamAdministrator(Long userId, Long teamId);

    /**
     * 删除团队成员信息
     * @param teamId 团队Id
     * @return 状态
     */
    boolean delTeamMemberInfoByTeamId(Long teamId);

    /**
     * 更新团队成员信息
     * @param teamMember 团队成员参数
     * @return 更新状态
     */
    boolean updateTeamMemberInfo(TeamMember teamMember);

    /**
     * 根据用户Id查询所有团队Id
     * @param userId 用户Id
     * @return 团队Id
     */
    List<Long> listTeamIdByUserId(Long userId);

    /**
     * 移除团队用户
     * @param teamId 团队Id
     * @param userId 用户Id
     * @return 状态
     */
    boolean removeTeamMemberUserByTeamId(Long teamId, Long userId);

    /**
     * 批量移除团队用户
     * @param teamIdList 团队id集合
     * @param userId 用户Id
     * @return 移除状态
     */
    boolean batchRemoveTeamMemberUser(List<Long> teamIdList, Long userId);

    /**
     * 搜索团队成员信息
     * @param teamId 团队Id
     * @param pageIndex 起始页
     * @param pageSize 每页显示记录条数
     * @return 团队信息
     */
    IPage<TeamMemberDTO> searchListTeamMemberInfo(Long teamId, int pageIndex, int pageSize);
}
