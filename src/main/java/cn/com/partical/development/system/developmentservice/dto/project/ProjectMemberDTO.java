package cn.com.partical.development.system.developmentservice.dto.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/30 13:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "项目成员参数")
public class ProjectMemberDTO implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "项目成员类型 0：创建者，1：管理员，2：编辑员，3：阅读员")
    private Integer projectMemberType;
}
