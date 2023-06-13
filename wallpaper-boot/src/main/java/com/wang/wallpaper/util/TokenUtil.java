package com.wang.wallpaper.util;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.wang.wallpaper.constants.exception.NotLoginException;
import com.wang.wallpaper.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-10-10 14:56
 */

public class TokenUtil {

   private static byte[] key = "1234567890".getBytes();

    public static String createToken(User user){
        // 密钥
        byte[] key = "1234567890".getBytes();
        long expiresTime = 24 * 60 * 60 * 1000 * 30;
        String token = JWT.create()
                .setPayload("username", user.getUsername())
                .setPayload("userId", user.getUserId())
                .setPayload("nickName", user.getNickname())
                .setPayload("type", user.getType())
                .setKey(key)
                .setExpiresAt(new Date(System.currentTimeMillis() + expiresTime))
                .sign();
        return token;
    }

    public static User parseToken(String token){

        try {
            JWT jwt = JWTUtil.parseToken(token);
            if (jwt.setKey(key).verify()) {
                JSONObject payloads = jwt.getPayloads();
                User user = JSON.parseObject(payloads.toString(), User.class);
                return user;
            }else {
                throw new NotLoginException(403,"登录已失效~");
            }
        }catch (Exception e){
            throw new NotLoginException(401,"未登录~");
        }

    }
}
