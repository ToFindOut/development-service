package cn.com.partical.development.system.developmentservice.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/18 9:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "登录参数信息")
public class UserLoginDTO implements Serializable {

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String pwd;
}
