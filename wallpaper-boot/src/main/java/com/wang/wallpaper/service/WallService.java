package com.wang.wallpaper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wallpaper.entity.UserComment;
import com.wang.wallpaper.entity.Wall;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.wallpaper.pojo.UserCommentVO;
import com.wang.wallpaper.pojo.WallDTO;
import com.wang.wallpaper.pojo.WallDetailVO;
import com.wang.wallpaper.pojo.WallVO;

/**
* @author Administrator
* @description 针对表【t_wall】的数据库操作Service
* @createDate 2022-09-29 16:49:44
*/
public interface WallService extends IService<Wall> {


    Page<WallVO> selectList(Wall wall, int pageSize, int currentPage);


    WallDetailVO detail(Long wallId);


    Page<WallDTO> userWallList(int pageSize, int currentPage, String keywords, Long categoryId, String sort);

    void favoriteWall(WallDTO wallDTO);

    void cancelFavoriteWall(Long id);

    Page<UserCommentVO> wallComment(int commentPageSize, int commentCurrentPage, Long wallId);

    void userComment(UserComment userComment);

    Page<WallDTO> wallUserList(int pageSize, int currentPage);
}
