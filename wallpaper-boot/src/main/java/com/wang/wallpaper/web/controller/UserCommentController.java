package com.wang.wallpaper.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wallpaper.constants.R;
import com.wang.wallpaper.constants.anno.UserAuth;
import com.wang.wallpaper.entity.User;
import com.wang.wallpaper.entity.UserComment;
import com.wang.wallpaper.pojo.UserCommentVO;
import com.wang.wallpaper.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-10-20 17:40
 */
@RestController
@RequestMapping("comment")
public class UserCommentController extends BaseController{
    @Autowired
    private UserCommentService userCommentService;
    @UserAuth
    @GetMapping()
    public R list(@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                             @RequestParam(value = "currentPage", defaultValue = "1") int currentPage
    ) {

        Page<UserCommentVO> list = userCommentService.selectList(pageSize, currentPage);
        return R.Page(list);
    }

    @PostMapping
    @UserAuth
    public R save(@RequestBody UserComment userComment) {

        userCommentService.saveOrUpdate(userComment);

        return R.success(userComment);
    }

}
