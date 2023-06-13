package com.wang.wallpaper.pojo;

import com.wang.wallpaper.entity.Wall;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-10-17 13:35
 */
@Data
@ToString
public class WallVO extends Wall {

    private List<String> wallPathList;

    private String categoryName;

    private String createByName;


    public List<String> getWallPathList() {
        String wallPath = getWallPath();
        if (StringUtils.isNotBlank(wallPath)){
            return Arrays.asList(wallPath);
        }
        return wallPathList;
    }

    public void setWallPathList(List<String> wallPathList) {
        this.wallPathList = wallPathList;
    }
}
