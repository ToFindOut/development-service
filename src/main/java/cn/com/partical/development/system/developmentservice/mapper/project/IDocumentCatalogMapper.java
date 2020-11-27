package cn.com.partical.development.system.developmentservice.mapper.project;

import cn.com.partical.development.system.developmentservice.dto.project.DocumentCatalogDTO;
import cn.com.partical.development.system.developmentservice.entity.DocumentCatalog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/27 14:02
 */
@Mapper
public interface IDocumentCatalogMapper extends BaseMapper<DocumentCatalog> {

    /**
     * 每页查询目录列表
     * @param superCatalogId 父目录ID
     * @param projectId 项目ID
     * @param page 分页参数
     * @return 文档目录列表参数
     */
    @Select("SELECT\n" +
            "id,\n" +
            "catalog_name AS catalogName\n" +
            "   FROM\n" +
            "document_catalog\n" +
            "   WHERE\n" +
            "project_id = #{projectId}\n" +
            "   AND super_catalog_id = #{superCatalogId}\n" +
            "   AND is_delete = 0")
    IPage<DocumentCatalogDTO> listCatalog(Page<Object> page,
                                          Long superCatalogId, Long projectId);
}
