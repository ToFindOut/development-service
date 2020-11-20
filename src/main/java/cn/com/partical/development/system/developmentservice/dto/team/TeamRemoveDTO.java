package cn.com.partical.development.system.developmentservice.dto.team;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/20 10:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "团队移除参数")
public class TeamRemoveDTO implements Serializable {

    @ApiModelProperty(value = "团队ID")
    private Long teamId;

    @ApiModelProperty(value = "团队用户Id")
    private Long teamUserId;

    @ApiModelProperty(value = "是否从我的所有团队中移除 0 : 否 1 : 是")
    private Integer type;

}
