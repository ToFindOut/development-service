package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目成员表
 */
@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProjectMember extends BasicsEntity {

  /**
   * 项目ID
   */
  private long projectId;

  /**
   * 用户ID
   */
  private long userId;

  /**
   * 项目成员类型 0：创建者，1：管理员，2：编辑员，3：阅读员
   */
  private Integer projectMemberType;

  /**
   * 项目成员状态 0：正常，1：冻结
   */
  private byte projectMemberState;

  // 标注 0: 未标注，1: 标注
  private byte flag;

}
