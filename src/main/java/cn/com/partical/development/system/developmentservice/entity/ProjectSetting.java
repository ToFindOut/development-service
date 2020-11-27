package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目配置表
 */
@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProjectSetting extends BasicsEntity {

  /**
   * 项目设置
   */
  private String projectSetting;

  /**
   * 项目id
   */
  private long projectId;

}
