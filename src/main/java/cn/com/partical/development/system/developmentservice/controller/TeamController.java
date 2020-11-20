package cn.com.partical.development.system.developmentservice.controller;

import cn.com.partical.development.system.developmentservice.base.BaseController;
import cn.com.partical.development.system.developmentservice.common.global.GlobalApiResponse;
import cn.com.partical.development.system.developmentservice.dto.team.TeamAddMemberDTO;
import cn.com.partical.development.system.developmentservice.dto.team.TeamRemoveDTO;
import cn.com.partical.development.system.developmentservice.dto.team.TeamSettingDTO;
import cn.com.partical.development.system.developmentservice.dto.team.TeamUpdateMemberDTO;
import cn.com.partical.development.system.developmentservice.util.api.ResponseUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/19 15:28
 */
@RestController
@RequestMapping("/team")
@Api(tags = "团队模块管理")
public class TeamController extends BaseController {


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



        return ResponseUtil.error("更新失败");
    }

    @ApiOperation(value = "解散团队")
    @RequestMapping(value = "/delete/{teamId}", method = RequestMethod.GET)
    public GlobalApiResponse<String> delete(HttpServletRequest request,
                                            @PathVariable Long teamId) {

        return ResponseUtil.error("解散失败");
    }

    @ApiOperation(value = "添加成员")
    @RequestMapping(value = "/add/member", method = RequestMethod.POST)
    public GlobalApiResponse<String> addMember(HttpServletRequest request,
                                               @RequestBody TeamAddMemberDTO teamAddMemberDTO) {

        return ResponseUtil.error("添加失败");
    }

    @ApiOperation(value = "修改身份")
    @RequestMapping(value = "/update/member", method = RequestMethod.POST)
    public GlobalApiResponse<String> updateMember(HttpServletRequest request,
                                                  @RequestBody TeamUpdateMemberDTO teamUpdateMemberDTO) {

        return ResponseUtil.error("修改失败");
    }

    @ApiOperation(value = "移除团队")
    @RequestMapping(value = "/remove/member", method = RequestMethod.POST)
    public GlobalApiResponse<String> removeMember(HttpServletRequest request,
                                                  @RequestBody TeamRemoveDTO teamRemoveDTO) {

        return ResponseUtil.error("修改失败");
    }

    @ApiOperation(value = "团队成员列表")
    @RequestMapping(value = "/list/member/{teamId}", method = RequestMethod.GET)
    public GlobalApiResponse<String> listMember(HttpServletRequest request,
                                                @PathVariable Long teamId) {

        return ResponseUtil.error("失败");
    }
}
