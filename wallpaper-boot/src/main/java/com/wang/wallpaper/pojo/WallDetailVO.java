package com.wang.wallpaper.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wang.wallpaper.entity.Wall;
import lombok.Data;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-10-10 13:42
 */
@Data
public class WallDetailVO extends Wall {

    /**
     * 作者名称
     */
    @TableField(exist = false)
    private String createName;
}
