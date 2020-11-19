package cn.com.partical.development.system.developmentservice.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/18 11:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户基本信息")
public class UserBaseInfoDTO implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private long id;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "用户昵称")
    private String userNickName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户头像地址")
    private String avatarUrl;

    @ApiModelProperty(value = "性别 0：未知，1：男，2：女")
    private Byte gender;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date birthday;

    @ApiModelProperty(value = "身份证")
    private String identityCard;

    private String code;
}
