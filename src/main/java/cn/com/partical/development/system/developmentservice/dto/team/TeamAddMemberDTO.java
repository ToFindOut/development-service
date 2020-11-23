package cn.com.partical.development.system.developmentservice.dto.team;

import cn.com.partical.development.system.developmentservice.entity.ProjectInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author 旺仔
 * @version 1.0
 * @date 2020/11/20 9:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "添加团队成员参数")
public class TeamAddMemberDTO {

    @ApiModelProperty(value = "被添加者用户Id")
    private Long userId;

    @ApiModelProperty(value = "团队id")
    private Long teamId;

    @ApiModelProperty(value = "成员类型 0: 管理员，1 : 普通成员")
    private Byte teamMemberType;

    @ApiModelProperty(value = "项目信息")
    private List<TeamProjectDTO> projectInfoList;

}
