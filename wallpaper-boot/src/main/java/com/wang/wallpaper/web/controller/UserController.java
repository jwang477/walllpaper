package com.wang.wallpaper.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wallpaper.constants.R;
import com.wang.wallpaper.constants.anno.UserAuth;
import com.wang.wallpaper.entity.User;
import com.wang.wallpaper.pojo.UserCommentVO;
import com.wang.wallpaper.pojo.UserVO;
import com.wang.wallpaper.pojo.WallDTO;
import com.wang.wallpaper.service.UserCommentService;
import com.wang.wallpaper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-09-29 16:24
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private UserCommentService userCommentService;
    /**
     * 用户列表
     * @param user
     * @param pageSize
     * @param currentPage
     * @return
     */
    @GetMapping
    @UserAuth
    public R list(User user,
                  @RequestParam(value = "pageSize",defaultValue = "5") int pageSize,
                  @RequestParam(value = "currentPage",defaultValue = "1") int currentPage){
       Page<User> userList =  userService.selectList(user,pageSize,currentPage);
       return R.Page(userList);
    }
    @GetMapping("userInfo")
    @UserAuth
    public R userInfo(@RequestParam(value = "username") String username){
        User user =  userService.userInfo(username);
        return R.success(user);
    }
    @GetMapping("{id}")
    @UserAuth
    public R userInfo(@PathVariable(value = "id") Long userId){
        User user =  userService.getById(userId);
        return R.success(user);
    }
    /**
     * 用户登录
     * @param user
     * @return
     */
    @PostMapping("login")
    public R login(@RequestBody UserVO user){

        UserVO vo = userService.login(user);

        return R.success(vo);
    }

    /**
     * 保存
     * @param userList
     * @return
     */
    @PostMapping
    @UserAuth
    public R save(@RequestBody List<User> userList) {

        userService.saveOrUpdateBatch(userList);

        return R.success(userList);
    }

    @PostMapping("/register")
    public R register(@RequestBody User user) {

        UserVO vo = userService.register(user);

        return R.success(vo);
    }
    /**
     * 批量删除
     * @param deleteList
     * @return
     */
    @DeleteMapping
    @UserAuth
    public R delete(@RequestBody List<Long> deleteList) {

        userService.removeBatchByIds(deleteList);

        return R.ok();
    }


    /**
     * 修改密码
     * @param user
     * @return
     */
    @PostMapping("update-password")
    @UserAuth
    public R updatePassword(@RequestBody UserVO user) {

        userService.updatePassword(user);

        return R.ok();
    }


    @UserAuth
    @GetMapping("/wall")
    public R wallUserList(@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                          @RequestParam(value = "currentPage", defaultValue = "1") int currentPage
    ) {

        Page<WallDTO> list = userService.userWallList(pageSize, currentPage);
        return R.Page(list);
    }

    @UserAuth
    @GetMapping("/comment")
    public R commentUserList(@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                          @RequestParam(value = "currentPage", defaultValue = "1") int currentPage
    ) {

        Page<UserCommentVO> list = userService.commentUserList(pageSize, currentPage);
        return R.Page(list);
    }
    @UserAuth
    @DeleteMapping("/comment/{id}")
    public R commentUserList(@PathVariable(value = "id") Long commentId) {

        userCommentService.removeById(commentId);
        return R.ok();
    }
}
