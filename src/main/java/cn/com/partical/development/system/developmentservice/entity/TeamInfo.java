package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 团队表
 */
@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
public class TeamInfo extends BasicsEntity {

  /**
   * 团队名
    */
  private String teamName;

  /**
   * 团队状态 0：正常，1：冻结
   */
  private byte teamState;

}
