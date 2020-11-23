package cn.com.partical.development.system.developmentservice.controller;

import cn.com.partical.development.system.developmentservice.base.BaseController;
import cn.com.partical.development.system.developmentservice.common.global.GlobalApiResponse;
import cn.com.partical.development.system.developmentservice.dto.project.ProjectSearchResultDTO;
import cn.com.partical.development.system.developmentservice.service.project.IProjectService;
import cn.com.partical.development.system.developmentservice.util.api.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 11:15
 */
@RestController
@RequestMapping("/project")
@Api(tags = "项目模块管理")
public class ProjectController extends BaseController {

    @Autowired
    private IProjectService projectService;

    @ApiOperation(value = "搜索项目信息")
    @RequestMapping(value = "/search/project/{teamId}", method = RequestMethod.GET)
    public GlobalApiResponse<ProjectSearchResultDTO> searchProject(HttpServletRequest request,
                                                                   @PathVariable Long teamId,
                                                                   @RequestParam String projectName) {

        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        return ResponseUtil.success(projectService.searchProjectInfoByTeamId(teamId, projectName));
    }

}
