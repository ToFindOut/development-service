package cn.com.partical.development.system.developmentservice.dto.team;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 15:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "团队成员参数信息")
public class TeamMemberDTO implements Serializable {

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "成员类型 0：管理员，1：普通成员")
    private Integer teamMemberType;
}
