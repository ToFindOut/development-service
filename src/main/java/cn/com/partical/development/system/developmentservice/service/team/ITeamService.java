package cn.com.partical.development.system.developmentservice.service.team;

import cn.com.partical.development.system.developmentservice.dto.team.TeamInfoDTO;
import cn.com.partical.development.system.developmentservice.dto.team.TeamMemberDTO;
import cn.com.partical.development.system.developmentservice.dto.team.TeamSettingDTO;
import cn.com.partical.development.system.developmentservice.entity.TeamInfo;
import cn.com.partical.development.system.developmentservice.entity.TeamMember;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/20 14:45
 */
@Service
public interface ITeamService extends IService<TeamInfo> {

    /**
     * 更新团队信息
     * @param teamSettingDTO 团队信息
     * @return 更新状态
     */
//    boolean updateTeamInfo(TeamSettingDTO teamSettingDTO);

    /**
     * 搜索用户所在的团队
     * @param teamName 团队名称
     * @param userId 用户Id
     * @param pageIndex 起始页
     * @param pageSize 每页显示记录条数
     * @return 团队信息
     */
    IPage<TeamInfoDTO> searchListTeamInfo(String teamName, Long userId, int pageIndex, int pageSize);
}
