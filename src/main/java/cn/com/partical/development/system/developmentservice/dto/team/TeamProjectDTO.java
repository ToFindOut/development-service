package cn.com.partical.development.system.developmentservice.dto.team;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/20 10:11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "团队项目参数")
public class TeamProjectDTO implements Serializable {

    @ApiModelProperty(value = "项目Id")
    private Long projectId;

    @ApiModelProperty(value = "项目成员类型 0：创建者，1：管理员，2：编辑员，3：阅读员")
    private Integer projectMemberType;

}
