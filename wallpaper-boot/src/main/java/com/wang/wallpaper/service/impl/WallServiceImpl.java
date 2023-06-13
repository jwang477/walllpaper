package com.wang.wallpaper.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.wallpaper.constants.exception.NotLoginException;
import com.wang.wallpaper.entity.User;
import com.wang.wallpaper.entity.UserComment;
import com.wang.wallpaper.entity.UserFavorite;
import com.wang.wallpaper.entity.Wall;
import com.wang.wallpaper.mapper.WallMapper;
import com.wang.wallpaper.pojo.UserCommentVO;
import com.wang.wallpaper.pojo.WallDTO;
import com.wang.wallpaper.pojo.WallDetailVO;
import com.wang.wallpaper.pojo.WallVO;
import com.wang.wallpaper.service.UserCommentService;
import com.wang.wallpaper.service.UserFavoriteService;
import com.wang.wallpaper.service.WallService;
import com.wang.wallpaper.util.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Administrator
 * @description 针对表【t_wall】的数据库操作Service实现
 * @createDate 2022-09-29 16:49:44
 */
@Service
public class WallServiceImpl extends ServiceImpl<WallMapper, Wall>
        implements WallService {

    @Resource
    private WallMapper wallMapper;

    @Autowired
    private UserFavoriteService userFavoriteService;

    @Autowired
    private UserCommentService userCommentService;

    @Override
    public Page<WallVO> selectList(Wall wall, int pageSize, int currentPage) {
        Page<WallVO> wallPage = new Page<>();
        wallPage.setSize(pageSize);
        wallPage.setCurrent(currentPage);
        wallPage = wallMapper.pageList(wallPage, wall);
        //wallPage = this.page(wallPage, wrapper);
        return wallPage;
    }

    @Override
    public WallDetailVO detail(Long wallId) {


        WallDetailVO detail = wallMapper.detail(wallId);

        return detail;
    }

    @Override
    public Page<WallDTO> userWallList(int pageSize, int currentPage, String keywords, Long categoryId, String sort) {
        Page<WallDTO> wallPage = new Page<>();
        wallPage.setSize(pageSize);
        wallPage.setCurrent(currentPage);
        User user = UserThreadLocal.get();
        Long userId = Objects.nonNull(user) ? user.getUserId() : null;
        wallPage = wallMapper.userWallList(wallPage, keywords, categoryId, userId, sort);
        return wallPage;
    }


    @Override
    public void favoriteWall(WallDTO wallDTO) {
        UserFavorite favorite = new UserFavorite();
        favorite.setWallId(wallDTO.getWallId());
        User user = UserThreadLocal.get();
        if (Objects.isNull(user)) {
            throw new NotLoginException(401, "登录已失效~");
        }
        favorite.setUserId(user.getUserId());
        userFavoriteService.save(favorite);
    }

    @Override
    public void cancelFavoriteWall(Long id) {
        User user = UserThreadLocal.get();
        if (Objects.isNull(user)) {
            throw new NotLoginException(401, "登录已失效~");
        }
        userFavoriteService.removeById(id);
    }

    @Override
    public Page<UserCommentVO> wallComment(int commentPageSize, int commentCurrentPage, Long wallId) {
        Page<UserCommentVO> wallCommentPage = new Page<>();
        wallCommentPage.setSize(commentPageSize);
        wallCommentPage.setCurrent(commentCurrentPage);
        User user = UserThreadLocal.get();
        Long userId = Objects.nonNull(user) ? user.getUserId() : null;
        wallCommentPage = wallMapper.wallComment(wallCommentPage,wallId,userId);
        return wallCommentPage;
    }

    /**
     * 发布评论
     * @param userComment
     */
    @Override
    public void userComment(UserComment userComment) {
        userCommentService.saveOrUpdate(userComment);
    }

    @Override
    public Page<WallDTO> wallUserList(int pageSize, int currentPage) {
        Page<WallDTO> wallPage = new Page<>();
        wallPage.setSize(pageSize);
        wallPage.setCurrent(currentPage);
        User user = UserThreadLocal.get();
        if (Objects.isNull(user)) {
            throw new NotLoginException(401, "登录已失效~");
        }
        wallPage = wallMapper.wallUserList(wallPage,user.getUserId());
        return wallPage;
    }
}




