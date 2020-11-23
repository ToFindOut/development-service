package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 团队成员表
 */
@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
public class TeamMember extends BasicsEntity {

  // 团队ID
  private long teamId;

  // 用户ID
  private long userId;

  // 成员类型 0：管理员，1：普通成员
  private byte teamMemberType;

  // 成员状态 0：正常，1：冻结
  private byte teamMemberState;

}
