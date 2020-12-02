package cn.com.partical.development.system.developmentservice.service.project;

import cn.com.partical.development.system.developmentservice.dto.project.ProjectCatalogLeftListDTO;
import cn.com.partical.development.system.developmentservice.entity.DocumentInfo;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/27 14:03
 */
public interface IDocumentInfoService extends IService<DocumentInfo> {

    /**
     * 通过目录ID查询文档信息
     * @param catalogId 目录ID
     * @return 文档信息
     */
    List<DocumentInfo> findDocumentInfoByCatalogId(Long catalogId);

    /**
     * 通过项目ID查询一级文档信息
     * @param projectId 项目ID
     * @return 文档信息
     */
    List<DocumentInfo> findStairDocumentInfoByProjectId(Long projectId);

    /**
     * 删除文档信息
     * @param id 文档ID
     * @return boolean
     */
    boolean delDocumentInfoById(Long id);

    /**
     * 根据文档ID查询文档内容
     * @param id 文档ID
     * @return json数据
     */
    JSONObject findDocumentContentById(Long id);

    /**
     * 查询文档信息
     * @param id 主键ID
     * @return 文档信息
     */
    DocumentInfo findDocumentInfo(Long id);
}
