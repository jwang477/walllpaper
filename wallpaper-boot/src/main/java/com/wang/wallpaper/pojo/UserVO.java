package com.wang.wallpaper.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-10-10 14:12
 */
@Data
public class UserVO {

    private Long userId;
    private Integer type;

    private String username;

    private String nickname;

    @JSONField(serialize = false)
    private String password;
    @JSONField(serialize = false)
    private String oldPassword;

    private String token;

    private String headPortrait;
}
