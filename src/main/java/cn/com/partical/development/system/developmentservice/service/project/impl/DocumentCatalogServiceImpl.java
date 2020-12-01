package cn.com.partical.development.system.developmentservice.service.project.impl;

import cn.com.partical.development.system.developmentservice.dto.project.DocumentCatalogDTO;
import cn.com.partical.development.system.developmentservice.dto.project.ProjectCatalogLeftListDTO;
import cn.com.partical.development.system.developmentservice.entity.DocumentCatalog;
import cn.com.partical.development.system.developmentservice.entity.DocumentInfo;
import cn.com.partical.development.system.developmentservice.mapper.project.IDocumentCatalogMapper;
import cn.com.partical.development.system.developmentservice.service.project.IDocumentCatalogService;
import cn.com.partical.development.system.developmentservice.service.project.IDocumentInfoService;
import cn.com.partical.development.system.developmentservice.util.filed.ActiveFlagEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/27 13:55
 */
@Service
public class DocumentCatalogServiceImpl extends ServiceImpl<IDocumentCatalogMapper,DocumentCatalog>implements IDocumentCatalogService {

    @Autowired
    private IDocumentCatalogMapper documentCatalogMapper;

    @Autowired
    private IDocumentInfoService documentInfoService;

    @Override
    public IPage<DocumentCatalogDTO> listCatalog(Long superCatalogId, Long projectId, Integer pageIndex, Integer pageSize) {
        Page<Object> page = new Page<>(pageIndex, pageSize);
        return documentCatalogMapper.listCatalog(page, superCatalogId, projectId);
    }

    @Override
    public List<ProjectCatalogLeftListDTO> listLeftDocumentCatalogInfo(Long projectId) {

        // 查询一级目录信息
        QueryWrapper<DocumentCatalog> documentCatalogQueryWrapper = new QueryWrapper<>();
        documentCatalogQueryWrapper.eq("project_id", projectId).eq("super_catalog_id", 0)
                .eq("is_delete", ActiveFlagEnum.DEFAULT.getValue());

        List<DocumentCatalog> documentCatalogList = documentCatalogMapper.selectList(documentCatalogQueryWrapper);

        List<ProjectCatalogLeftListDTO> projectCatalogLeftListDTOList = this.function(documentCatalogList);

        // 查询没有目录的文档信息
        List<DocumentInfo> stairDocumentInfo = documentInfoService.findStairDocumentInfoByProjectId(projectId);

        // 添加文档信息
        projectCatalogLeftListDTOList.addAll(this.documentInfoListFunction(stairDocumentInfo));

        return projectCatalogLeftListDTOList;
    }

    /**
     * 递归查询子目录及文档信息
     */
    private List<ProjectCatalogLeftListDTO> recursionListLeftDocumentCatalogInfo(Long parentCatalogId) {
        // 查询子级目录信息
        QueryWrapper<DocumentCatalog> documentCatalogQueryWrapper = new QueryWrapper<>();
        documentCatalogQueryWrapper.eq("super_catalog_id", parentCatalogId).eq("is_delete", ActiveFlagEnum.DEFAULT.getValue());

        List<DocumentCatalog> documentCatalogList = documentCatalogMapper.selectList(documentCatalogQueryWrapper);

        return this.function(documentCatalogList);
    }

    /**
     * 公共函数
     * @param documentCatalogList 文档目录信息
     * @return 封装信息
     */
    private List<ProjectCatalogLeftListDTO> function(List<DocumentCatalog> documentCatalogList) {
        List<ProjectCatalogLeftListDTO>  projectCatalogLeftListDTOList = new LinkedList<>();

        documentCatalogList.forEach(n->{

            ProjectCatalogLeftListDTO projectCatalogLeftListDTO = new ProjectCatalogLeftListDTO();
            projectCatalogLeftListDTO.setCatalogId(n.getId());
            projectCatalogLeftListDTO.setCatalogName(n.getCatalogName());

            // 通过目录ID查询文档信息
            List<DocumentInfo> documentInfoByCatalog = documentInfoService.findDocumentInfoByCatalogId(n.getId());

            List<ProjectCatalogLeftListDTO> documentInfoList = new LinkedList<>();

            // 添加文档信息
            documentInfoList.addAll(this.documentInfoListFunction(documentInfoByCatalog));

            // 子目录及文档信息
            documentInfoList.addAll(this.recursionListLeftDocumentCatalogInfo(n.getId()));

            projectCatalogLeftListDTO.setSubjectProList(documentInfoList);

            projectCatalogLeftListDTOList.add(projectCatalogLeftListDTO);
        });

        return projectCatalogLeftListDTOList;
    }

    /**
     * 公共代码 文档信息函数
     * @param documentInfoLists 参数
     * @return 文档信息
     */
    private List<ProjectCatalogLeftListDTO> documentInfoListFunction(List<DocumentInfo> documentInfoLists) {
        // 创建集合 封装子集文档以及目录信息
        List<ProjectCatalogLeftListDTO> documentInfoList = new LinkedList<>();

        // 文档信息
        documentInfoLists.forEach(x -> {
            ProjectCatalogLeftListDTO documentInfo= new ProjectCatalogLeftListDTO();
            documentInfo.setDocumentId(x.getId());
            documentInfo.setDocumentName(x.getDocumentName());

            documentInfoList.add(documentInfo);
        });
        
        return documentInfoList;
    }
}
