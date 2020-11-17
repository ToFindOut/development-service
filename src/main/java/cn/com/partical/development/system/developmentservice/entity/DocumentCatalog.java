package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 需求池表
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DocumentCatalog extends BasicsEntity {

  // 目录名
  private String catalogName;

  //父目录ID
  private long superCatalogId;

  // 项目ID
  private long projectId;

}
