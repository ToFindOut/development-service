package cn.com.partical.development.system.developmentservice.dto.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/24 14:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "项目成员管理")
public class ProjectMemberUpdateDTO implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "项目ID")
    private Long projectId;

    @ApiModelProperty(value = "项目成员类型 0：创建者，1：管理员，2：编辑员，3：阅读员")
    private Integer projectMemberType;

    @ApiModelProperty(value = "0:设置成员身份 1:从项目中移除")
    private Integer type;
}
