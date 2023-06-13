package com.wang.wallpaper.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_user_comment
 */
@TableName(value ="t_user_comment")
@Data
public class UserComment implements Serializable {
    /**
     * 
     */
    @TableId(value = "comment_id", type = IdType.AUTO)
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long commentId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id",fill = FieldFill.INSERT)
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long userId;

    /**
     * 壁纸ID
     */
    @TableField(value = "wall_id")
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long wallId;

    /**
     * 评论
     */
    @TableField(value = "comment")
    private String comment;

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
     * 评论审核状态 0：审核中 1：审核通过  2：审核不通过
     */
    @TableField(value = "status")
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserComment that = (UserComment) o;

        return new EqualsBuilder().append(commentId, that.commentId).append(userId, that.userId).append(wallId, that.wallId).append(comment, that.comment).append(createTime, that.createTime).append(updateTime, that.updateTime).append(status, that.status).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(commentId).append(userId).append(wallId).append(comment).append(createTime).append(updateTime).append(status).toHashCode();
    }

    @Override
    public String toString() {
        return "UserComment{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", wallId=" + wallId +
                ", comment='" + comment + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}