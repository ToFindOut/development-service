package cn.com.partical.development.system.developmentservice.dto.team;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 16:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "团队参数信息")
public class TeamInfoDTO implements Serializable {

    @ApiModelProperty(value = "团队id")
    private Long teamId;

    @ApiModelProperty(value = "团队名称")
    private String teamName;

    @ApiModelProperty(value = "团队状态 0：正常，1：冻结")
    private Byte teamState;
}
