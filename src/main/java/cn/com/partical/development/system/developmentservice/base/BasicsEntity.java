package cn.com.partical.development.system.developmentservice.base;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 * @author wang
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BasicsEntity implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用ID
     */
    @TableField(value = "app_id",fill = FieldFill.INSERT)
    private Long appId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", strategy = FieldStrategy.NOT_NULL, fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 最后修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /**
     * 数据状态
     */
    @TableField(value = "active_flag", fill = FieldFill.INSERT)
    private Short activeFlag;

    @TableField(value = "version", fill = FieldFill.INSERT)
    @Version
    private Long version;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
