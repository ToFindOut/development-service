package cn.com.partical.development.system.developmentservice.dto.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/23 11:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "项目搜索结果参数")
public class ProjectSearchResultDTO {

    @ApiModelProperty(value = "项目Id")
    private Long projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

}
