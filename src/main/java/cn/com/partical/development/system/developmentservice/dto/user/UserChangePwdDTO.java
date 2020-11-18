package cn.com.partical.development.system.developmentservice.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/18 17:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户修改密码参数信息")
public class UserChangePwdDTO {

    @ApiModelProperty(value = "旧密码")
    private String oldPwd;

    @ApiModelProperty(value = "新密码")
    private String newPwd;

    @ApiModelProperty(value = "确认密码")
    private String reNewPwd;
}
