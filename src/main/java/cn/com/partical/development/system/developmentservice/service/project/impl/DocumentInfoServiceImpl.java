package cn.com.partical.development.system.developmentservice.service.project.impl;

import cn.com.partical.development.system.developmentservice.entity.DocumentInfo;
import cn.com.partical.development.system.developmentservice.mapper.project.IDocumentInfoMapper;
import cn.com.partical.development.system.developmentservice.service.project.IDocumentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/27 14:04
 */
@Service
public class DocumentInfoServiceImpl extends ServiceImpl<IDocumentInfoMapper,DocumentInfo> implements IDocumentInfoService {
}
