package cn.com.partical.development.system.developmentservice.dto.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/27 14:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "文档目录列表参数")
public class DocumentCatalogDTO implements Serializable {

    @ApiModelProperty(value = "目录ID")
    private Long id;

    @ApiModelProperty(value = "目录名")
    private String catalogName;
}
