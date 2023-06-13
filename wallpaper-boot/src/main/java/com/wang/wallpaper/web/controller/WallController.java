package com.wang.wallpaper.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wallpaper.constants.R;
import com.wang.wallpaper.constants.anno.UserAuth;
import com.wang.wallpaper.constants.anno.VisitAuth;
import com.wang.wallpaper.entity.UserComment;
import com.wang.wallpaper.entity.Wall;
import com.wang.wallpaper.pojo.UserCommentVO;
import com.wang.wallpaper.pojo.WallDTO;
import com.wang.wallpaper.pojo.WallDetailVO;
import com.wang.wallpaper.pojo.WallVO;
import com.wang.wallpaper.service.WallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-10-10 11:43
 */
@RestController
@RequestMapping("wall")
public class WallController extends BaseController {

    @Autowired
    private WallService wallService;

    /**
     * 后台壁纸列表
     * @param wall
     * @param pageSize
     * @param currentPage
     * @return
     */
    @GetMapping
    public R list(Wall wall,
                  @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                  @RequestParam(value = "currentPage", defaultValue = "1") int currentPage) {

        Page<WallVO> wallPage = wallService.selectList(wall, pageSize, currentPage);
        return R.Page(wallPage);
    }

    /**
     * 详情
     *
     * @param wallId
     * @return
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable(value = "id") Long wallId) {
        WallDetailVO detail = wallService.detail(wallId);
        return R.success(detail);
    }

    /**
     * 保存
     *
     * @param walls
     * @return
     */
    @PostMapping
    @UserAuth
    public R save(@RequestBody Wall wall) {

        wallService.saveOrUpdate(wall);
        return R.success(wall);
    }

    /**
     * 批量保存
     * @param walls
     * @return
     */
    @PostMapping("batch")
    @UserAuth
    public R saveBatch(@RequestBody List<Wall> walls) {

        wallService.saveOrUpdateBatch(walls);
        return R.success(walls);
    }

    /**
     * 删除
     *
     * @param wallIdList
     * @return
     */
    @DeleteMapping("batch")
    @UserAuth
    public R deleteBatch(@RequestBody List<Long> wallIdList) {

        wallService.removeBatchByIds(wallIdList);

        return R.ok();
    }

    /**
     * 删除壁纸
     * @param wallId
     * @return
     */
    @DeleteMapping("{id}")
    @UserAuth
    public R delete(@PathVariable(value = "id") Long wallId) {

        wallService.removeById(wallId);

        return R.ok();
    }

    /**
     * 前台列表
     * @param pageSize
     * @param currentPage
     * @param keywords
     * @param categoryId
     * @param sort
     * @return
     */
    @VisitAuth
    @GetMapping("/font")
    public R userWallList(@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                          @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                          @RequestParam(value = "keywords", required = false) String keywords,
                          @RequestParam(value = "categoryId", required = false) Long categoryId,
                          @RequestParam(value = "sort", defaultValue = "sort") String sort) {

        Page<WallDTO> list = wallService.userWallList(pageSize, currentPage, keywords, categoryId, sort);
        return R.Page(list);
    }

    /**
     * 收藏壁纸
     * @param wallDTO
     * @return
     */
    @VisitAuth
    @PostMapping("/favorite")
    public R favoriteWall(@RequestBody WallDTO wallDTO) {

        wallService.favoriteWall(wallDTO);
        return R.success(wallDTO);
    }

    /**
     * 取消收藏
     * @param Id
     * @return
     */
    @VisitAuth
    @DeleteMapping("/favorite")
    public R cancelFavoriteWall(@RequestBody Long Id) {

        wallService.cancelFavoriteWall(Id);
        return R.ok();
    }

    /**
     * 壁纸评论列表
     * @param commentPageSize
     * @param commentCurrentPage
     * @param wallId
     * @return
     */
    @VisitAuth
    @GetMapping("/comment/{wallId}")
    public R wallComment(@RequestParam(value = "commentPageSize", defaultValue = "5") int commentPageSize,
                         @RequestParam(value = "commentCurrentPage", defaultValue = "1") int commentCurrentPage,
                         @PathVariable(value = "wallId") Long wallId) {

        Page<UserCommentVO> list = wallService.wallComment(commentPageSize, commentCurrentPage, wallId);
        return R.Page(list);
    }
    /**
     * 发布评论
     * @param userComment
     */
    @UserAuth
    @PostMapping("/comment")
    public R userComment(@RequestBody UserComment userComment) {

         wallService.userComment(userComment);
        return R.success(userComment);
    }

    /**
     * 收藏
     * @param pageSize
     * @param currentPage
     * @return
     */
    @UserAuth
    @GetMapping("/user")
    public R wallUserList(@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                          @RequestParam(value = "currentPage", defaultValue = "1") int currentPage
                          ) {

        Page<WallDTO> list = wallService.wallUserList(pageSize, currentPage);
        return R.Page(list);
    }
}
