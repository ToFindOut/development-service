package cn.com.partical.development.system.developmentservice.dto.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/25 10:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "项目更新参数")
public class ProjectSaveOrUpdateDTO implements Serializable {

    @ApiModelProperty(value = "目录ID,为空时新增,有值时修改")
    private Long id;

    @ApiModelProperty(value = "目录名")
    private String catalogName;

    @ApiModelProperty(value = "父目录ID,默认为0是一级目录")
    private Long superCatalogId;

    @ApiModelProperty(value = "项目ID")
    private Long projectId;

}
