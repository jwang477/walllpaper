package com.wang.wallpaper.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;

/**
 * 
 * @TableName t_wall
 */
@TableName(value ="t_wall")
@Data
public class Wall implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "wall_id", type = IdType.AUTO)
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long wallId;

    /**
     * 壁纸分类ID
     */
    @TableField(value = "category_id")
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long categoryId;

    /**
     * 壁纸名称
     */
    @TableField(value = "wall_name")
    private String wallName;

    /**
     * 壁纸链接
     */
    @TableField(value = "wall_path")
    private String wallPath;

    /**
     * 壁纸简介
     */
    @TableField(value = "wall_brief")
    private String wallBrief;

    /**
     * 壁纸排名
     */
    @TableField(value = "sort")
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Integer sort;

    /**
     * 壁纸审核状态 0审核中 1审核通过  2审核未通过
     */
    @TableField(value = "status")
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 壁纸作者
     */
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long createBy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Wall other = (Wall) that;
        return (this.getWallId() == null ? other.getWallId() == null : this.getWallId().equals(other.getWallId()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getWallName() == null ? other.getWallName() == null : this.getWallName().equals(other.getWallName()))
            && (this.getWallPath() == null ? other.getWallPath() == null : this.getWallPath().equals(other.getWallPath()))
            && (this.getWallBrief() == null ? other.getWallBrief() == null : this.getWallBrief().equals(other.getWallBrief()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWallId() == null) ? 0 : getWallId().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getWallName() == null) ? 0 : getWallName().hashCode());
        result = prime * result + ((getWallPath() == null) ? 0 : getWallPath().hashCode());
        result = prime * result + ((getWallBrief() == null) ? 0 : getWallBrief().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", wallId=").append(wallId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", wallName=").append(wallName);
        sb.append(", wallPath=").append(wallPath);
        sb.append(", wallBrief=").append(wallBrief);
        sb.append(", sort=").append(sort);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @TableField(exist = false)
    private List<String> wallPathList;

    public List<String> getWallPathList() {
        String wallPath = getWallPath();
        if (StringUtils.isNotBlank(wallPath)){
            return Arrays.asList(wallPath);
        }
        return wallPathList;
    }
}