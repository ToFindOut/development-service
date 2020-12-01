package cn.com.partical.development.system.developmentservice.service.project.impl;

import cn.com.partical.development.system.developmentservice.entity.DocumentInfo;
import cn.com.partical.development.system.developmentservice.mapper.project.IDocumentInfoMapper;
import cn.com.partical.development.system.developmentservice.service.project.IDocumentInfoService;
import cn.com.partical.development.system.developmentservice.util.filed.ActiveFlagEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/27 14:04
 */
@Service
public class DocumentInfoServiceImpl extends ServiceImpl<IDocumentInfoMapper,DocumentInfo> implements IDocumentInfoService {

    @Autowired
    private IDocumentInfoMapper documentInfoMapper;

    @Override
    public List<DocumentInfo> findDocumentInfoByCatalogId(Long catalogId) {
        QueryWrapper<DocumentInfo> documentInfoQueryWrapper = new QueryWrapper<>();
        documentInfoQueryWrapper.eq("catalog_id", catalogId).eq("is_delete", ActiveFlagEnum.DEFAULT.getValue());

        return documentInfoMapper.selectList(documentInfoQueryWrapper);
    }

    @Override
    public List<DocumentInfo> findStairDocumentInfoByProjectId(Long projectId) {
        QueryWrapper<DocumentInfo> documentInfoQueryWrapper = new QueryWrapper<>();
        documentInfoQueryWrapper.eq("project_id", projectId).eq("catalog_id", 0).eq("is_delete", ActiveFlagEnum.DEFAULT.getValue());

        return documentInfoMapper.selectList(documentInfoQueryWrapper);
    }
}
