package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 企业用户表
 */
@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
public class OrgUser extends BasicsEntity {

  // 企业ID
  private long orgId;

  // 用户ID
  private long userId;

}
