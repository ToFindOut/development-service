package cn.com.partical.development.system.developmentservice.dto.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/24 14:10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "项目列表 / 修改参数")
public class ProjectUpdateDTO implements Serializable {

    @ApiModelProperty(value = "项目ID")
    private Long id;

    @ApiModelProperty(value = "项目名")
    private String projectName;

    @ApiModelProperty(value = "项目介绍")
    private String projectIntroduce;

    @ApiModelProperty(value = "开放类型 0：私有，1：团队可见，2：项目成员可见，3：企业内可见，4：公开")
    private Integer openType;

    @ApiModelProperty(value = "项目状态 0：正常，1：冻结")
    private Integer projectState;
}
