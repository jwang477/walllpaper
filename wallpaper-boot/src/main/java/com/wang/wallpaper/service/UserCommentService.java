package com.wang.wallpaper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wallpaper.entity.UserComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.wallpaper.pojo.UserCommentVO;

/**
* @author Administrator
* @description 针对表【t_user_comment】的数据库操作Service
* @createDate 2022-09-29 16:49:44
*/
public interface UserCommentService extends IService<UserComment> {

    Page<UserCommentVO> selectList(int pageSize, int currentPage);
}
