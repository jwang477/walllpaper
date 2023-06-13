package com.wang.wallpaper.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wang.wallpaper.entity.User;
import com.wang.wallpaper.util.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-10-10 10:38
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);

        User user = UserThreadLocal.get();
        if (user != null) {
            this.setFieldValByName("createBy", user.getUserId(), metaObject);
            this.setFieldValByName("userId", user.getUserId(), metaObject);

        }


    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
