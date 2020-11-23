package cn.com.partical.development.system.developmentservice.mapper.team;

import cn.com.partical.development.system.developmentservice.dto.team.TeamInfoDTO;
import cn.com.partical.development.system.developmentservice.entity.TeamInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 9:53
 */
@Mapper
public interface ITeamMapper extends BaseMapper<TeamInfo> {

    /**
     * 搜索团队信息
     * @param page 分页参数
     * @param teamName 团队名称
     * @return 团队信息
     */
    @Select("SELECT\n" +
            "    a.id AS teamtId, \n" +
            "    a.team_name AS teamtName, \n" +
            "    a.team_state AS teamState \n" +
            "FROM\n" +
            "    team_info a\n" +
            "    LEFT JOIN team_member b ON a.id = b.team_id \n" +
            "WHERE\n" +
            "    a.team_name LIKE '%teamName%' \n" +
            "    AND a.is_delete = 0 \n" +
            "    AND b.is_delete = 0 \n" +
            "    AND b.user_id = #{userId}\n" +
            "ORDER BY\n" +
            "    a.create_time DESC")
    IPage<TeamInfoDTO> searchListTeamInfo(Page<Object> page, @Param("teamName") String teamName, @Param("userId") Long userId);
}
