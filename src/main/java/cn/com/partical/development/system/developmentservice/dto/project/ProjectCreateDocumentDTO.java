package cn.com.partical.development.system.developmentservice.dto.project;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/25 11:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "创建文档参数")
public class ProjectCreateDocumentDTO implements Serializable {

    @ApiModelProperty(value = "目录ID,为空时新增,有值时修改")
    private Long id;

    @ApiModelProperty(value = "项目ID")
    private Long projectId;

    @ApiModelProperty(value = "文件名")
    private String documentName;

    @ApiModelProperty(value = "文档类型 0：HTTP文档，1：Markdown文档")
    private Integer documentType;

    @ApiModelProperty(value = "目录ID")
    private Long catalogId;

    @ApiModelProperty(value = "文档内容")
    private JSONObject documentContent;
}
