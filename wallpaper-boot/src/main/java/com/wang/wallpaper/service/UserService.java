package com.wang.wallpaper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wallpaper.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.wallpaper.pojo.UserCommentVO;
import com.wang.wallpaper.pojo.UserVO;
import com.wang.wallpaper.pojo.WallDTO;

/**
* @author Administrator
* @description 针对表【t_user】的数据库操作Service
* @createDate 2022-09-29 16:49:44
*/
public interface UserService extends IService<User> {

    Page<User> selectList(User user, int pageSize, int currentPage);

    UserVO login(UserVO user);

    void updatePassword(UserVO user);

    User userInfo(String username);

    Page<WallDTO> userWallList(int pageSize, int currentPage);

    Page<UserCommentVO> commentUserList(int pageSize, int currentPage);

    UserVO register(User user);
}
