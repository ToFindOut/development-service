package cn.com.partical.development.system.developmentservice.service.team.impl;

import cn.com.partical.development.system.developmentservice.dto.team.TeamInfoDTO;
import cn.com.partical.development.system.developmentservice.dto.team.TeamSettingDTO;
import cn.com.partical.development.system.developmentservice.entity.TeamInfo;
import cn.com.partical.development.system.developmentservice.entity.TeamMember;
import cn.com.partical.development.system.developmentservice.mapper.team.ITeamMapper;
import cn.com.partical.development.system.developmentservice.service.team.ITeamService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/20 14:45
 */
@Service
public class TeamServiceImpl extends ServiceImpl<ITeamMapper,TeamInfo> implements ITeamService {

    @Autowired
    private ITeamMapper teamMapper;

//    @Override
//    public boolean updateTeamInfo(TeamSettingDTO teamSettingDTO) {
//        TeamInfo teamInfo = new TeamInfo();
//        BeanUtil.copyProperties(teamSettingDTO, teamInfo);
//        return teamMapper.updateById(teamInfo) > 0;
//    }

    @Override
    public IPage<TeamInfoDTO> searchListTeamInfo(String teamName, Long userId, int pageIndex, int pageSize) {
        Page<Object> page = new Page<>(pageIndex, pageSize);
        return teamMapper.searchListTeamInfo(page, teamName, userId);
    }


}
