package com.wang.wallpaper.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wallpaper.entity.UserComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.wallpaper.pojo.UserCommentVO;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【t_user_comment】的数据库操作Mapper
* @createDate 2022-09-29 16:49:44
* @Entity com.wang.wallpaper.entity.UserComment
*/
public interface UserCommentMapper extends BaseMapper<UserComment> {

    Page<UserCommentVO> pageList(@Param("commentVOPage") Page<UserCommentVO> commentVOPage);

}




