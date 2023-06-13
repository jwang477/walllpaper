package com.wang.wallpaper.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wallpaper.entity.Wall;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.wallpaper.pojo.UserCommentVO;
import com.wang.wallpaper.pojo.WallDTO;
import com.wang.wallpaper.pojo.WallDetailVO;
import com.wang.wallpaper.pojo.WallVO;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【t_wall】的数据库操作Mapper
* @createDate 2022-09-29 16:49:44
* @Entity com.wang.wallpaper.entity.Wall
*/
public interface WallMapper extends BaseMapper<Wall> {

    WallDetailVO detail(@Param("wallId") Long wallId);

    Page<WallVO> pageList(@Param("wallPage") Page<WallVO> wallPage, @Param("wall") Wall wall);

    Page<WallDTO> userWallList(@Param("wallPage") Page<WallDTO> wallPage,
                               @Param("keywords") String keywords,
                               @Param("categoryId") Long categoryId,
                               @Param("userId") Long userId, @Param("sort") String sort);

    Page<UserCommentVO> wallComment(@Param("wallCommentPage") Page<UserCommentVO> wallCommentPage,@Param("wallId") Long wallId,@Param("userId") Long userId);

    Page<WallDTO> wallUserList(@Param("wallPage") Page<WallDTO> wallPage, @Param("userId") Long userId);
}




