package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文档版本记录表
 */
@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
public class DocumentVersion extends BasicsEntity {

  // 文档ID
  private long documentId;

  // 版本号
  private long documentVersion;

  // 文档内容 json
  private String documentContent;

}
