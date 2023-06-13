package com.wang.wallpaper.pojo;

import com.wang.wallpaper.entity.UserComment;
import lombok.Data;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-10-20 10:08
 */
@Data
public class UserCommentVO extends UserComment {


    private String username;

    private String nickname;

    private String headPortrait;

    private String wallPath;

    private String wallName;


}
