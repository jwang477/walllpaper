package com.wang.wallpaper.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wallpaper.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.wallpaper.pojo.UserCommentVO;
import com.wang.wallpaper.pojo.WallDTO;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2022-09-29 16:49:44
* @Entity com.wang.wallpaper.entity.User
*/
public interface UserMapper extends BaseMapper<User> {

    Page<WallDTO> userWallList(@Param("wallPage") Page<WallDTO> wallPage, @Param("userId") Long userId);

    Page<UserCommentVO> commentUserList(@Param("commentVOPage") Page<UserCommentVO> commentVOPage, @Param("userId") Long userId);
}




