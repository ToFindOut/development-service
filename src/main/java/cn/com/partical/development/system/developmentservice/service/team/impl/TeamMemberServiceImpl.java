package cn.com.partical.development.system.developmentservice.service.team.impl;

import cn.com.partical.development.system.developmentservice.common.constant.ITeamConstant;
import cn.com.partical.development.system.developmentservice.dto.team.TeamMemberDTO;
import cn.com.partical.development.system.developmentservice.entity.TeamMember;
import cn.com.partical.development.system.developmentservice.mapper.team.ITeamMemberMapper;
import cn.com.partical.development.system.developmentservice.service.team.ITeamMemberService;
import cn.com.partical.development.system.developmentservice.util.filed.ActiveFlagEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 10:13
 */
@Service
public class TeamMemberServiceImpl implements ITeamMemberService {

    @Autowired
    private ITeamMemberMapper teamMemberMapper;

    @Override
    public boolean checkUserIsTeamAdministrator(Long userId, Long teamId) {
        QueryWrapper<TeamMember> teamMemberQueryWrapper = new QueryWrapper<>();
        teamMemberQueryWrapper.eq("team_id", teamId).eq("user_id", userId).eq("active_flag", ActiveFlagEnum.DEFAULT.getValue())
        .eq("team_member_type", ITeamConstant.TEAM_MEMBER_TYPE_ADMINISTRATOR);

        return teamMemberMapper.selectCount(teamMemberQueryWrapper) < 1;
    }

    @Override
    public boolean delTeamMemberInfoByTeamId(Long teamId) {
        return teamMemberMapper.delTeamMemberInfoByTeamId(teamId) > 0;
    }

    @Override
    public boolean updateTeamMemberInfo(TeamMember teamMember) {
        return teamMemberMapper.updateById(teamMember) > 0;
    }

    @Override
    public List<Long> listTeamIdByUserId(Long userId) {
        return teamMemberMapper.listTeamIdByUserId(userId);
    }

    @Override
    public boolean removeTeamMemberUserByTeamId(Long teamId, Long userId) {
        return teamMemberMapper.removeTeamMemberUserByTeamId(teamId, userId) > 0;
    }

    @Override
    public boolean batchRemoveTeamMemberUser(List<Long> teamIdList, Long userId) {
        return teamMemberMapper.batchRemoveTeamMemberUser(teamIdList, userId) > 0;
    }

    @Override
    public IPage<TeamMemberDTO> searchListTeamMemberInfo(Long teamId, int pageIndex, int pageSize) {
        Page<Object> page = new Page<>(pageIndex, pageSize);
        return teamMemberMapper.searchListTeamMemberInfo(page, teamId);
    }
}
