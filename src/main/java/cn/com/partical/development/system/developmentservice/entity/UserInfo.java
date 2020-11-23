package cn.com.partical.development.system.developmentservice.entity;

import cn.com.partical.development.system.developmentservice.base.BasicsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户表
 */
@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfo extends BasicsEntity {

  // 用户昵称
  private String userNickName;

  // 手机号
  private String phone;

  // 密码
  private String pwd;

  // 邮箱
  private String email;

  // 用户头像地址
  private String avatarUrl;

  // 用户状态 0：正常，1：冻结
  private byte userState;

  // 性别 0：未知，1：男，2：女
  private byte gender;

  // 城市
  private String city;

  // 省份
  private String province;

  // 国家
  private String country;

  // 生日
  private Date birthday;

  // 身份证
  private String identityCard;

}
