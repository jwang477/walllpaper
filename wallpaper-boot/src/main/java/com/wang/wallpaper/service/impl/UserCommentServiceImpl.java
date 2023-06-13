package com.wang.wallpaper.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.wallpaper.constants.exception.NotLoginException;
import com.wang.wallpaper.entity.User;
import com.wang.wallpaper.entity.UserComment;
import com.wang.wallpaper.pojo.UserCommentVO;
import com.wang.wallpaper.service.UserCommentService;
import com.wang.wallpaper.mapper.UserCommentMapper;
import com.wang.wallpaper.util.UserThreadLocal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
* @author Administrator
* @description 针对表【t_user_comment】的数据库操作Service实现
* @createDate 2022-09-29 16:49:44
*/
@Service
public class UserCommentServiceImpl extends ServiceImpl<UserCommentMapper, UserComment>
    implements UserCommentService{

    @Resource
    private UserCommentMapper userCommentMapper;

    @Override
    public Page<UserCommentVO> selectList(int pageSize, int currentPage) {
        Page<UserCommentVO> commentVOPage = new Page<>();
        commentVOPage.setSize(pageSize);
        commentVOPage.setCurrent(currentPage);
        commentVOPage = userCommentMapper.pageList(commentVOPage);
        return commentVOPage;
    }
}




