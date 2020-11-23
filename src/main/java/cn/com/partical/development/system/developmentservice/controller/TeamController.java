package cn.com.partical.development.system.developmentservice.controller;

import cn.com.partical.development.system.developmentservice.base.BaseController;
import cn.com.partical.development.system.developmentservice.common.constant.ITeamConstant;
import cn.com.partical.development.system.developmentservice.common.global.GlobalApiResponse;
import cn.com.partical.development.system.developmentservice.dto.team.*;
import cn.com.partical.development.system.developmentservice.entity.ProjectMember;
import cn.com.partical.development.system.developmentservice.entity.TeamInfo;
import cn.com.partical.development.system.developmentservice.entity.TeamMember;
import cn.com.partical.development.system.developmentservice.service.project.IProjectMemberService;
import cn.com.partical.development.system.developmentservice.service.team.ITeamMemberService;
import cn.com.partical.development.system.developmentservice.service.team.ITeamService;
import cn.com.partical.development.system.developmentservice.util.api.ResponseUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/19 15:28
 */
@RestController
@RequestMapping("/team")
@Api(tags = "团队模块管理")
public class TeamController extends BaseController {

    @Autowired
    private ITeamService teamService;

    @Autowired
    private ITeamMemberService teamMemberService;

    @Autowired
    private IProjectMemberService projectMemberService;

    @ApiOperation(value = "更新团队")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public GlobalApiResponse<String> update(HttpServletRequest request,
                                            @RequestBody TeamSettingDTO teamSettingDTO) {

        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        if (teamSettingDTO == null || StrUtil.isBlank(teamSettingDTO.getTeamName())) {
            return ResponseUtil.error("必填参数不能为空");
        }

        TeamInfo teamInfo = new TeamInfo();
        BeanUtil.copyProperties(teamSettingDTO, teamInfo);

        if (teamService.saveOrUpdate(teamInfo)) {
            return ResponseUtil.error("更新成功");
        }

        return ResponseUtil.error("更新失败");
    }

    @ApiOperation(value = "解散团队")
    @RequestMapping(value = "/delete/{teamId}", method = RequestMethod.GET)
    public GlobalApiResponse<String> delete(HttpServletRequest request,
                                            @PathVariable Long teamId) {

        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        // 检查用户是否是团队管理员
        if (teamMemberService.checkUserIsTeamAdministrator(userId,teamId)) {
            return ResponseUtil.error(801, "权限不足, 仅管理员才可以设置");
        }

        // 解散团队成员
        if (teamMemberService.delTeamMemberInfoByTeamId(teamId)) {
            return ResponseUtil.success("解散成功");
        }

        return ResponseUtil.error("解散失败");
    }

    @ApiOperation(value = "添加成员")
    @RequestMapping(value = "/add/member", method = RequestMethod.POST)
    public GlobalApiResponse<String> addMember(HttpServletRequest request,
                                               @RequestBody TeamAddMemberDTO teamAddMemberDTO) {

        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        if (teamAddMemberDTO == null || teamAddMemberDTO.getUserId() == null || teamAddMemberDTO.getTeamId() == null
                || teamAddMemberDTO.getTeamMemberType() == null || teamAddMemberDTO.getProjectInfoList().size() < 1) {
            return ResponseUtil.error(403, "参数不能为空");
        }

        // 检查用户是否是团队管理员
        if (teamMemberService.checkUserIsTeamAdministrator(userId,teamAddMemberDTO.getTeamId())) {
            return ResponseUtil.error(801, "权限不足, 仅管理员才可以设置");
        }

        // 添加团队成员表
        TeamMember teamMember = new TeamMember();
        teamMember.setUserId(teamAddMemberDTO.getTeamId());
        teamMember.setTeamId(teamAddMemberDTO.getTeamId());
        teamMember.setTeamMemberType(teamAddMemberDTO.getTeamMemberType());

        List<ProjectMember> projectMemberList = new LinkedList<>();
        BeanUtil.copyProperties(teamAddMemberDTO.getProjectInfoList(), projectMemberList);

        if (this.saveMemberInfo(teamMember, projectMemberList)) {
            return ResponseUtil.success("添加成功");
        }

        return ResponseUtil.error("添加失败");
    }

    @Transactional
    boolean saveMemberInfo(TeamMember teamMember, List<ProjectMember> projectMemberList) {
        return teamMemberService.updateTeamMemberInfo(teamMember) && projectMemberService.saveProjectMemberInfo(projectMemberList);
    }



    @ApiOperation(value = "修改身份")
    @RequestMapping(value = "/update/member", method = RequestMethod.POST)
    public GlobalApiResponse<String> updateMember(HttpServletRequest request,
                                                  @RequestBody TeamUpdateMemberDTO teamUpdateMemberDTO) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        if (teamUpdateMemberDTO == null || teamUpdateMemberDTO.getTeamId() == null
                || teamUpdateMemberDTO.getUserId() == null
                || (teamUpdateMemberDTO.getTeamMemberType() != ITeamConstant.TEAM_MEMBER_TYPE_ADMINISTRATOR
                && teamUpdateMemberDTO.getTeamMemberType() != ITeamConstant.TEAM_MEMBER_TYPE_GENERAL)) {
            return ResponseUtil.error(403, "参数错误");
        }

        // 检查用户是否是团队管理员
        if (teamMemberService.checkUserIsTeamAdministrator(userId,teamUpdateMemberDTO.getTeamId())) {
            return ResponseUtil.error(801, "权限不足, 仅管理员才可以设置");
        }

        TeamMember teamMember = new TeamMember();

        BeanUtil.copyProperties(teamUpdateMemberDTO, teamMember);

        if (teamMemberService.updateTeamMemberInfo(teamMember)) {
            return ResponseUtil.success("修改成功");
        }

        return ResponseUtil.error("修改失败");
    }

    @ApiOperation(value = "移除团队")
    @RequestMapping(value = "/remove/member", method = RequestMethod.POST)
    public GlobalApiResponse<String> removeMember(HttpServletRequest request,
                                                  @RequestBody TeamRemoveDTO teamRemoveDTO) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        if (teamRemoveDTO == null || teamRemoveDTO.getTeamId() == null || teamRemoveDTO.getTeamUserId() == null) {
            return ResponseUtil.error(403, "参数错误");
        }

        // 检查用户是否是团队管理员
        if (teamMemberService.checkUserIsTeamAdministrator(userId,teamRemoveDTO.getTeamId())) {
            return ResponseUtil.error(801, "权限不足, 仅管理员才可以设置");
        }

        switch (teamRemoveDTO.getType()) {
            case ITeamConstant.TEAM_MEMBER_REMOVE_STATE_CURRENT:
                // 将成员移除当前团队
                if (teamMemberService.removeTeamMemberUserByTeamId(teamRemoveDTO.getTeamId(), teamRemoveDTO.getTeamUserId())) {
                    return ResponseUtil.success("移除成功");
                }
                break;
            case ITeamConstant.TEAM_MEMBER_REMOVE_STATE_ALL:
                // 查看我的所有团队
                List<Long> myTeamIds = teamMemberService.listTeamIdByUserId(userId);
                if (myTeamIds.size() < 1) {
                    return ResponseUtil.error(406, "当前没有团队");
                }
                if (teamMemberService.batchRemoveTeamMemberUser(myTeamIds, userId)) {
                    return ResponseUtil.success("移除成功");
                }
                break;
            default:
                return ResponseUtil.error(405, "类型错误");
        }

        return ResponseUtil.error("移除失败");
    }

    @ApiOperation(value = "团队成员列表")
    @RequestMapping(value = "/list/team/member/{teamId}", method = RequestMethod.GET)
    public GlobalApiResponse<TeamMemberDTO> listTeamMemberInfo(HttpServletRequest request,
                                                       @PathVariable Long teamId,
                                                       @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }



        return ResponseUtil.success(teamMemberService.searchListTeamMemberInfo(teamId, pageIndex, pageSize));
    }

    @ApiOperation(value = "团队列表")
    @RequestMapping(value = "/list/team", method = RequestMethod.GET)
    public GlobalApiResponse<TeamMemberDTO> listTeamInfo(HttpServletRequest request,
                                                       @RequestParam String teamName,
                                                       @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        return ResponseUtil.success(teamService.searchListTeamInfo(teamName, userId, pageIndex, pageSize));
    }
}
