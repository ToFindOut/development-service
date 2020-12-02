package cn.com.partical.development.system.developmentservice.service.project;

import cn.com.partical.development.system.developmentservice.dto.project.DocumentCatalogDTO;
import cn.com.partical.development.system.developmentservice.dto.project.ProjectCatalogLeftListDTO;
import cn.com.partical.development.system.developmentservice.entity.DocumentCatalog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/27 13:54
 */
public interface IDocumentCatalogService extends IService<DocumentCatalog> {

    /**
     * 查询目录列表
     * @param superCatalogId 父目录ID
     * @param projectId 项目ID
     * @param pageIndex 页码
     * @param pageSize 每页显示记录条数
     * @return 文档目录列表参数
     */
    IPage<DocumentCatalogDTO> listCatalog(Long superCatalogId, Long projectId,
                                          Integer pageIndex, Integer pageSize);


    /**
     * 左侧文档目录列表
     * @param projectId 项目ID
     * @return 目录文档信息
     */
    List<ProjectCatalogLeftListDTO> listLeftDocumentCatalogInfo(Long projectId);

    /**
     * 删除目录信息
     * @param id 目录ID
     * @return boolean
     */
    boolean delCatalogInfoById(Long id);

    /**
     * 查询文档目录信息
     * @param id 目录ID
     * @return 目录信息
     */
    DocumentCatalog findDocumentCatalogInfo(Long id);
}
