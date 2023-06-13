package com.wang.wallpaper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.wallpaper.entity.UserFavorite;
import com.wang.wallpaper.service.UserFavoriteService;
import com.wang.wallpaper.mapper.UserFavoriteMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【t_user_favorite】的数据库操作Service实现
* @createDate 2022-09-29 16:49:44
*/
@Service
public class UserFavoriteServiceImpl extends ServiceImpl<UserFavoriteMapper, UserFavorite>
    implements UserFavoriteService{

}




