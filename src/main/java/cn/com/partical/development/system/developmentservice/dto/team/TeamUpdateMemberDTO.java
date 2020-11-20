package cn.com.partical.development.system.developmentservice.dto.team;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/20 10:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "修改身份信息")
public class TeamUpdateMemberDTO implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "团队Id")
    private Long teamId;

    @ApiModelProperty(value = "成员类型 0：管理员，1：普通成员")
    private Integer teamMemberType;
}
