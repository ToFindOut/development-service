package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文档表
 */
@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
public class DocumentInfo extends BasicsEntity {

  /**
   * 项目ID
    */
  private long projectId;

  /**
   * 目录ID 【0：无目录】
    */
  private long catalogId;

  /**
   * 文件名
    */
  private String documentName;

  /**
   * 文档类型 0：HTTP文档，1：Markdown文档
   */
  private byte documentType;

  /**
   * 文档内容
   */
  private String documentContent;

}
