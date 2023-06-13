package com.wang.wallpaper.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.wang.wallpaper.entity.Wall;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-10-17 13:35
 */
@Data
@ToString
public class WallDTO extends Wall {



    private String categoryName;

    private String createByName;

    private Integer favorite;
    private Long favoriteId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date favoriteTime;
}
