package cn.com.partical.development.system.developmentservice.service.project.impl;

import cn.com.partical.development.system.developmentservice.dto.project.DocumentCatalogDTO;
import cn.com.partical.development.system.developmentservice.entity.DocumentCatalog;
import cn.com.partical.development.system.developmentservice.mapper.project.IDocumentCatalogMapper;
import cn.com.partical.development.system.developmentservice.service.project.IDocumentCatalogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/27 13:55
 */
@Service
public class DocumentCatalogServiceImpl extends ServiceImpl<IDocumentCatalogMapper,DocumentCatalog>implements IDocumentCatalogService {

    @Autowired
    private IDocumentCatalogMapper documentCatalogMapper;

    @Override
    public IPage<DocumentCatalogDTO> listCatalog(Long superCatalogId, Long projectId, Integer pageIndex, Integer pageSize) {
        Page<Object> page = new Page<>(pageIndex, pageSize);
        return documentCatalogMapper.listCatalog(page, superCatalogId, projectId);
    }
}
