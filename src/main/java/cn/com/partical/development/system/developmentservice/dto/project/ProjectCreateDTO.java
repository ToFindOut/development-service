package cn.com.partical.development.system.developmentservice.dto.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/24 14:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "项目创建参数")
public class ProjectCreateDTO /*implements Serializable*/ {

    @ApiModelProperty(value = "团队ID")
    private Long teamId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "项目介绍")
    private String projectIntroduce;

    @ApiModelProperty(value = "开放类型 0：私有，1：团队可见，2：项目成员可见，3：企业内可见，4：公开")
    private Integer openType;
}
