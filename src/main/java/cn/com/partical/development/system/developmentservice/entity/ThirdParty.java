package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 第三方账号绑定表
 */
@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
public class ThirdParty extends BasicsEntity {

  /**
   * 用户ID
   */
  private long userId;

  /**
   * 第三方唯一ID
   */
  private String thirdPartyId;

  /**
   * 第三方详情
   */
  private String thirdPartyDetails;

  /**
   * 第三方类型 0：微信，1：支付宝
   */
  private byte thirdPartyType;

}
