package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 企业信息表
 */
@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
public class OrgInfo extends BasicsEntity {

  // 企业名
  private String orgName;

  // 管理员用户ID
  private long adminUserId;

  // 企业简称
  private String orgNickName;

  // 企业图标URL
  private String orgAvatarUrl;

  // 企业编号
  private String orgNumber;

  // 营业执照URL
  private String businessLicenseUrl;

}
