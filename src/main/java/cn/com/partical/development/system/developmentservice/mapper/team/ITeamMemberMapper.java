package cn.com.partical.development.system.developmentservice.mapper.team;

import cn.com.partical.development.system.developmentservice.dto.team.TeamMemberDTO;
import cn.com.partical.development.system.developmentservice.entity.TeamMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 10:11
 */
@Mapper
public interface ITeamMemberMapper extends BaseMapper<TeamMember> {

    /**
     * 删除团队成员信息
     *
     * @param teamId 团队Id
     * @return 状态
     */
    @Update("UPDATE team_member SET is_delete = 1 WHERE team_id = #{teamId}")
    int delTeamMemberInfoByTeamId(@Param("teamId") Long teamId);

    /**
     * 根据用户Id查询所有团队Id
     *
     * @param userId 用户Id
     * @return 团队Id
     */
    @Select("SELECT team_id FROM team_member WHERE  user_id = #{userId} AND active_flag = 0")
    List<Long> listTeamIdByUserId(@Param("userId") Long userId);

    /**
     * 移除团队用户
     * @param teamId 团队Id
     * @param userId 用户Id
     * @return 状态
     */
    @Update("UPDATE team_member SET is_delete = 1 WHERE team_id = #{teamId} AND user_id = #{userId}")
    int removeTeamMemberUserByTeamId(@Param("teamId") Long teamId, @Param("userId") Long userId);

    /**
     * 批量移除团队用户
     * @param teamIdList 团队id集合
     * @param userId 用户Id
     * @return 移除状态
     */
    @Update("<script>"+
            "UPDATE team_member SET is_delete = 1 WHERE user_id = #{userId} AND team_id IN \n"+
                "<foreach collection='teamIdList' item='id' open='(' separator=',' close=')'>" +
                    "#{id}\n" +
                "</foreach>" +
            "</script>")
    int batchRemoveTeamMemberUser(@Param("teamIdList") List<Long> teamIdList, @Param("userId") Long userId);

    /**
     * 搜索团队成员信息
     * @param page 分页参数
     * @param teamId 团队Id
     * @return 搜索结果
     */
    @Select("SELECT\n" +
            "    a.user_id AS userId,\n" +
            "    b.user_nick_name AS userName,\n" +
            "    a.team_member_type AS teamMemberType\n" +
            "FROM\n" +
            "    team_member a\n" +
            "    LEFT JOIN ( SELECT id, user_nick_name FROM user_info ) b ON a.user_id = b.id \n" +
            "WHERE\n" +
            "    a.is_delete = 0 \n" +
            "    AND a.team_id = #{teamId}")
    IPage<TeamMemberDTO> searchListTeamMemberInfo(Page<Object> page, @Param("teamId") Long teamId);
}
