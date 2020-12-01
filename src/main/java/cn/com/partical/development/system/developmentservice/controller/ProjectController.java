package cn.com.partical.development.system.developmentservice.controller;

import cn.com.partical.development.system.developmentservice.base.BaseController;
import cn.com.partical.development.system.developmentservice.common.constant.IProjectConstant;
import cn.com.partical.development.system.developmentservice.common.global.GlobalApiResponse;
import cn.com.partical.development.system.developmentservice.dto.project.*;
import cn.com.partical.development.system.developmentservice.entity.DocumentCatalog;
import cn.com.partical.development.system.developmentservice.entity.DocumentInfo;
import cn.com.partical.development.system.developmentservice.entity.ProjectInfo;
import cn.com.partical.development.system.developmentservice.entity.ProjectMember;
import cn.com.partical.development.system.developmentservice.service.project.IDocumentCatalogService;
import cn.com.partical.development.system.developmentservice.service.project.IDocumentInfoService;
import cn.com.partical.development.system.developmentservice.service.project.IProjectMemberService;
import cn.com.partical.development.system.developmentservice.service.project.IProjectService;
import cn.com.partical.development.system.developmentservice.service.team.ITeamMemberService;
import cn.com.partical.development.system.developmentservice.util.api.ResponseUtil;
import cn.com.partical.development.system.developmentservice.util.filed.ActiveFlagEnum;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    @Autowired
    private IProjectMemberService projectMemberService;

    @Autowired
    private ITeamMemberService teamMemberService;

    @Autowired
    private IDocumentCatalogService documentCatalogService;

    @Autowired
    private IDocumentInfoService documentInfoService;

    @ApiOperation(value = "搜索项目信息")
    @RequestMapping(value = "/search/{teamId}", method = RequestMethod.GET)
    public GlobalApiResponse<ProjectSearchResultDTO> search(HttpServletRequest request,
                                                                   @PathVariable Long teamId,
                                                                   @RequestParam String projectName) {

        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        return ResponseUtil.success(projectService.searchProjectInfoByTeamId(teamId, projectName));
    }

    @ApiOperation(value = "创建项目")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public GlobalApiResponse<String> create(HttpServletRequest request,
                                            @RequestBody ProjectCreateDTO projectCreateDTO) {

        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        if (projectCreateDTO == null || projectCreateDTO.getTeamId() == null || projectCreateDTO.getOpenType() == null || StrUtil.isBlank(projectCreateDTO.getProjectName())) {
            return ResponseUtil.error(403, "参数不能为空");
        }


        switch (projectCreateDTO.getOpenType()) {
            case IProjectConstant.PROJECT_DISPARK_TYPE_PRIVATE:
                break;
            case IProjectConstant.PROJECT_DISPARK_TYPE_TEAM_MEMBER:
                break;
            case IProjectConstant.PROJECT_DISPARK_TYPE_PROJECT_MEMBER:
                break;
            case IProjectConstant.PROJECT_DISPARK_TYPE_COMPANY:
                break;
            case IProjectConstant.PROJECT_DISPARK_TYPE_PUBLIC:
                break;
            default:
                return ResponseUtil.error(405, "参数类型错误");
        }

        // 检查当前用户权限 是否属于团队管理员或创建者
        if (teamMemberService.checkUserIsTeamAdministratorOrCreator(userId, projectCreateDTO.getTeamId())) {
            return ResponseUtil.error(801, "权限不足,仅管理员才可以创建");
        }

        ProjectInfo projectInfo = new ProjectInfo();
        BeanUtil.copyProperties(projectCreateDTO, projectInfo);

        if (projectService.save(projectInfo)) {
            ProjectMember projectMember = new ProjectMember();
            projectMember.setProjectId(projectInfo.getId());
            projectMember.setUserId(userId);
            projectMember.setProjectMemberType(IProjectConstant.PROJECT_MEMBER_TYPE_CREATE);
            projectMemberService.save(projectMember);

            return ResponseUtil.success("创建成功");
        }



        return ResponseUtil.error("创建失败");
    }

    @ApiOperation(value = "项目列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public GlobalApiResponse<ProjectUpdateDTO> list(HttpServletRequest request,
                                          @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                          @RequestParam String projectName) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        return ResponseUtil.success(projectService.listProjectInfo(userId, projectName, pageIndex, pageSize));
    }

    @ApiOperation(value = "项目成员列表")
    @RequestMapping(value = "/list/member/{projectId}", method = RequestMethod.GET)
    public GlobalApiResponse<ProjectMemberDTO> listMember(HttpServletRequest request,
                                                    @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                    @PathVariable Long projectId) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        return ResponseUtil.success(projectMemberService.listProjectMemberInfo(projectId, pageIndex, pageSize));
    }


    @ApiOperation(value = "修改项目")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public GlobalApiResponse<ProjectUpdateDTO> update(HttpServletRequest request,
                           @RequestBody ProjectUpdateDTO projectUpdateDTO) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        if (projectUpdateDTO == null || projectUpdateDTO.getId() == null) {
            return ResponseUtil.error(403, "参数不能为空");
        }

        ProjectInfo projectInfo = new ProjectInfo();
        BeanUtil.copyProperties(projectUpdateDTO, projectInfo);

        if (projectService.updateById(projectInfo)) {
            return ResponseUtil.success("修改成功");
        }


        return ResponseUtil.error("修改失败");
    }

    @ApiOperation(value = "删除项目")
    @RequestMapping(value = "/delete/{projectId}", method = RequestMethod.GET)
    public GlobalApiResponse<String> delete(HttpServletRequest request,
                                          @PathVariable Long projectId) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        // 检查当前操作人是否属于创建者 权限校验
        if (projectMemberService.checkUserIsProjectMemberCreator(userId, projectId)) {
            return ResponseUtil.error(801, "权限不足,仅项目创建者才可以删除项目");
        }

        if (projectService.deleteProjectInfoById(projectId) && projectMemberService.deleteProjectMemberInfoByProjectId(projectId)) {
            return ResponseUtil.success("删除成功");
        }

        return ResponseUtil.error("删除失败");
    }

    @ApiOperation(value = "成员管理")
    @RequestMapping(value = "/update/member", method = RequestMethod.POST)
    public GlobalApiResponse<String> updateMember(HttpServletRequest request,
                                            @RequestBody ProjectMemberUpdateDTO projectMemberUpdateDTO) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        if (projectMemberUpdateDTO == null || projectMemberUpdateDTO.getUserId() == null || projectMemberUpdateDTO.getProjectId() == null
                || projectMemberUpdateDTO.getProjectMemberType() == null || projectMemberUpdateDTO.getType() == null) {
            return ResponseUtil.error(405, "不能修改自己本身");
        }

        if (userId.equals(projectMemberUpdateDTO.getUserId())) {
            return ResponseUtil.error(403, "参数不能为空");
        }

        // 检查操作用户权限
        if (projectMemberService.checkUserIsProjectMemberCreatorOrAdministrator(userId, projectMemberUpdateDTO.getProjectId())) {
            return ResponseUtil.error(801, "权限不足, 仅创建者或管理员才可以操作");
        }

        // 检查被操作用户是否属于创建者，无法操作创建者
        if (!projectMemberService.checkUserIsProjectMemberCreator(projectMemberUpdateDTO.getUserId(), projectMemberUpdateDTO.getProjectId())) {
            return ResponseUtil.error(801, "权限不足, 无法越级操作");
        }

        switch (projectMemberUpdateDTO.getProjectMemberType()) {
            // TODO 不能设置成员类型为创建者
//            case IProjectConstant.PROJECT_MEMBER_TYPE_CREATE:
//                break;
            case IProjectConstant.PROJECT_MEMBER_TYPE_ADMINISTRATOR:
                break;
            case IProjectConstant.PROJECT_MEMBER_TYPE_EDITOR:
                break;
            case IProjectConstant.PROJECT_MEMBER_TYPE_READ:
                break;
            default:
                return ResponseUtil.error(405, "参数类型错误");
        }

        switch (projectMemberUpdateDTO.getType()) {
            case IProjectConstant.PROJECT_MEMBER_IDENTITY_TYPE_MEMBER:
                // TODO 修改成员身份 其实需要严格权限校验
                if (projectMemberService.updateProjectMemberIdentityInfo(projectMemberUpdateDTO.getUserId(), projectMemberUpdateDTO.getProjectId(), projectMemberUpdateDTO.getProjectMemberType())) {
                    return ResponseUtil.success("身份已更新");
                }
                break;
            case IProjectConstant.PROJECT_MEMBER_IDENTITY_TYPE_REMOVE:
                /*
                 * 需求：移除项目成员
                 * 思路：
                 *  1.判断当前操作用户是否属于创建者身份，如果是则直接移除即可
                 *  2.或则判断被移除用户是否属于管理员身份，不属于则直接移除
                 */
                if (!projectMemberService.checkUserIsProjectMemberCreator(userId, projectMemberUpdateDTO.getProjectId())
                        || projectMemberService.checkUserIsProjectMemberAdministrator(projectMemberUpdateDTO.getUserId(), projectMemberUpdateDTO.getProjectId())) {

                    if (projectMemberService.removeProjectMember(projectMemberUpdateDTO.getUserId(), projectMemberUpdateDTO.getProjectId())) {
                        return ResponseUtil.success("移除成功");
                    }

                }
                break;
            default:
                return ResponseUtil.error(405, "参数类型错误");
        }

        return ResponseUtil.error("操作失败");
    }

    @ApiOperation(value = "项目标注")
    @RequestMapping(value = "/mark/{projectId}", method = RequestMethod.GET)
    public GlobalApiResponse<String> mark(HttpServletRequest request,
                                          @PathVariable Long projectId) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        if (projectMemberService.markProject(userId, projectId)) {
            return ResponseUtil.success("标注成功");
        }

        return ResponseUtil.error("标注失败");
    }

    @ApiOperation(value = "新建/修改目录")
    @RequestMapping(value = "/update/catalog", method = RequestMethod.POST)
    public GlobalApiResponse<String> updateCatalog(HttpServletRequest request,
                                                   @RequestBody ProjectSaveOrUpdateDTO projectSaveOrUpdateDTO) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        if (projectSaveOrUpdateDTO == null || StrUtil.isBlank(projectSaveOrUpdateDTO.getCatalogName())
                || projectSaveOrUpdateDTO.getProjectId() == null || projectSaveOrUpdateDTO.getSuperCatalogId() == null) {
            return ResponseUtil.error(403, "参数不能为空");
        }

        // 权限校验 判断用户是否是创建者或管理员或编辑者
        if (projectMemberService.checkUserIsProjectMemberCreatorOrAdministrator(userId, projectSaveOrUpdateDTO.getProjectId())
                && projectMemberService.checkUserIsProjectMemberEditor(userId, projectSaveOrUpdateDTO.getProjectId())) {
            return ResponseUtil.error(801, "权限不足");
        }

        DocumentCatalog documentCatalog = new DocumentCatalog();
        BeanUtil.copyProperties(projectSaveOrUpdateDTO, documentCatalog);

        if (documentCatalogService.saveOrUpdate(documentCatalog)) {
            return ResponseUtil.success("更新成功");
        }

        return ResponseUtil.error("更新失败");
    }

    @ApiOperation(value = "删除目录")
    @RequestMapping(value = "/delete/catalog/{id}", method = RequestMethod.GET)
    public GlobalApiResponse<String> deleteCatalog(HttpServletRequest request,
                                          @PathVariable Long id) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        // 查询目录信息
        QueryWrapper<DocumentCatalog> documentCatalogQueryWrapper = new QueryWrapper<>();
        documentCatalogQueryWrapper.eq("id", id).eq("is_delete", ActiveFlagEnum.DEFAULT.getValue());

        DocumentCatalog documentCatalog = documentCatalogService.getOne(documentCatalogQueryWrapper);

        if (documentCatalog == null) {
            return ResponseUtil.error(403,"目录不存在");
        }

        // 检查用户权限
        if (projectMemberService.checkUserIsProjectMemberCreatorOrAdministrator(userId, documentCatalog.getProjectId())
                && projectMemberService.checkUserIsProjectMemberEditor(userId, documentCatalog.getProjectId())) {
            return ResponseUtil.error(801, "权限不足");
        }

        // 逻辑删除
        documentCatalog.setIsDelete(ActiveFlagEnum.DELETE.getValue());

        if (documentCatalogService.updateById(documentCatalog)) {
            return ResponseUtil.success("删除成功");
        }

        return ResponseUtil.error("删除失败");
    }

    @ApiOperation(value = "查询目录接口")
    @RequestMapping(value = "/list/catalog/{superCatalogId}/{projectId}", method = RequestMethod.GET)
    public GlobalApiResponse<DocumentCatalogDTO> listCatalog(HttpServletRequest request,
                                                   @PathVariable Long superCatalogId,
                                                   @PathVariable Long projectId,
                                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        return ResponseUtil.success(documentCatalogService.listCatalog(superCatalogId, projectId, pageIndex, pageSize));
    }

    @ApiOperation(value = "新建保存文档")
    @RequestMapping(value = "/create/document", method = RequestMethod.POST)
    public GlobalApiResponse<String> createDocument(HttpServletRequest request,
                                                   @RequestBody ProjectCreateDocumentDTO projectCreateDocumentDTO) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }

        if (projectCreateDocumentDTO == null || projectCreateDocumentDTO.getProjectId() == null || projectCreateDocumentDTO.getDocumentType() == null
                || StrUtil.isBlank(projectCreateDocumentDTO.getDocumentName())) {
            return ResponseUtil.error(403, "参数不能为空");
        }

        if (projectCreateDocumentDTO.getDocumentType() != IProjectConstant.DOCUMENT_TYPE_HTTP
                && projectCreateDocumentDTO.getDocumentType() != IProjectConstant.DOCUMENT_TYPE_MARKDOWN) {
            return ResponseUtil.error(405, "参数类型错误");
        }

        DocumentInfo documentInfo = new DocumentInfo();
        BeanUtil.copyProperties(projectCreateDocumentDTO, documentInfo);

        if (documentInfoService.saveOrUpdate(documentInfo)) {
            return ResponseUtil.success("保存成功");
        }

        return ResponseUtil.error("保存失败");
    }

    @ApiOperation(value = "左侧目录列表")
    @RequestMapping(value = "/left/catalog/{projectId}", method = RequestMethod.GET)
    public GlobalApiResponse<ProjectCatalogLeftListDTO> listLeftCatalogInfo(HttpServletRequest request,
                                                                            @PathVariable Long projectId) {
        Long userId = super.getUserId(request);

        if (userId == null) {
            return ResponseUtil.error(401, "用户身份已过期");
        }


        return ResponseUtil.success(documentCatalogService.listLeftDocumentCatalogInfo(projectId));
    }

    @ApiOperation(value = "保存文档信息")
    @RequestMapping(value = "/save/document/content", method = RequestMethod.POST)
    public GlobalApiResponse<String> saveDocumentContent(HttpServletRequest request,
                                                         @RequestBody JSONObject documentContent) {
        String s = documentContent.toString();
        System.out.println(s);
        System.out.println(documentContent.getLong("documentId"));
        return ResponseUtil.success(new JSONObject(s));
    }
}
