package cn.com.partical.development.system.developmentservice.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/18 13:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户注册信息")
public class UserRegisterInfoDTO implements Serializable {

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String pwd;

    @ApiModelProperty(value = "验证码")
    private String code;
}
