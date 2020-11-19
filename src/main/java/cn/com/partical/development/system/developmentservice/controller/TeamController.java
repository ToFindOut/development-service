package cn.com.partical.development.system.developmentservice.controller;

import cn.com.partical.development.system.developmentservice.base.BaseController;
import cn.com.partical.development.system.developmentservice.common.global.GlobalApiResponse;
import cn.com.partical.development.system.developmentservice.util.api.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public GlobalApiResponse<String> update(HttpServletRequest request) {

        return ResponseUtil.error("更新失败");
    }

    @ApiOperation(value = "解散团队")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public GlobalApiResponse<String> delete(HttpServletRequest request) {

        return ResponseUtil.error("解散失败");
    }


}
