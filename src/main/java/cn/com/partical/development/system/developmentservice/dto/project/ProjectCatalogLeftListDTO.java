package cn.com.partical.development.system.developmentservice.dto.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/12/1 13:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "项目目录左侧展示列表")
public class ProjectCatalogLeftListDTO implements Serializable {

    @ApiModelProperty(value = "目录Id")
    private Long catalogId;

    @ApiModelProperty(value = "目录名称")
    private String catalogName;

    @ApiModelProperty(value = "文档ID")
    private Long documentId;

    @ApiModelProperty(value = "文档名称")
    private String documentName;

    @ApiModelProperty(value = "父文档ID")
    private Long parentCatalogId;

    @ApiModelProperty(value = "子集")
    private List<ProjectCatalogLeftListDTO> subjectProList;
}
