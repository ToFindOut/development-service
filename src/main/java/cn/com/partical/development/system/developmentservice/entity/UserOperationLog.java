package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户操作记录表
 */
@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserOperationLog extends BasicsEntity {

  // 用户ID
  private long userId;

  // 操作唯一UUID
  private String operationUuid;

  // 请求URL
  private String requestUrl;

  // 请求数据
  private String requestData;

  // 响应数据
  private String responseData;

  // 方法调用链
  private String functionCallChain;

}
