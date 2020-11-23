package cn.com.partical.development.system.developmentservice.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 10:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户搜索基本参数信息")
public class UserSearchBaseDTO implements Serializable {

    @ApiModelProperty(value = "用户Id")
    private Long id;

    @ApiModelProperty(value = "用户昵称")
    private String userNickName;

    @ApiModelProperty(value = "用户手机号")
    private String userPhone;
}
