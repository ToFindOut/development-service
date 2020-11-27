package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文档标签表
 */
@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
public class DocumentLabel extends BasicsEntity {

  /**
   * 标签名
   */
  private String labelName;

  /**
   * 标签内容
   */
  private String labelContent;

  /**
   * 文档版本记录ID
   */
  private long documentVersionId;

}
