package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 需求池表
 */
@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
public class DemandPool extends BasicsEntity {

  /**
   * 需求名
   */
  private String demandName;

  /**
   * 需求详情
    */
  private String demandContent;

  /**
   * 需求类型 0：业务逻辑需求，1：页面展示需求
   */
  private byte demandType;

  /**
   * 需求提供人员id
   */
  private long userId;

}
