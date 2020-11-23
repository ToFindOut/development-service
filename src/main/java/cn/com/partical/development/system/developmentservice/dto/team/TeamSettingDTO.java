package cn.com.partical.development.system.developmentservice.dto.team;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/20 9:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "团队设置信息")
public class TeamSettingDTO implements Serializable {

    @ApiModelProperty(value = "团队id")
    private Long id;

    @ApiModelProperty(value = "团队名称")
    private String teamName;
}
