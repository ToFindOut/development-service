package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目表
 */
@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProjectInfo extends BasicsEntity {

  // 项目名
  private String projectName;

  // 项目介绍
  private String projectIntroduce;

  // 开放类型 0：私有，1：团队可见，2：项目成员可见，3：企业内可见，4：公开
  private byte openType;

  // 项目状态 0：正常，1：冻结
  private byte projectState;

}
