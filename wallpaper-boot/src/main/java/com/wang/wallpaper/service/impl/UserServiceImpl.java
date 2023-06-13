package com.wang.wallpaper.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.wallpaper.constants.exception.NotLoginException;
import com.wang.wallpaper.entity.User;
import com.wang.wallpaper.mapper.UserMapper;
import com.wang.wallpaper.pojo.UserCommentVO;
import com.wang.wallpaper.pojo.UserVO;
import com.wang.wallpaper.pojo.WallDTO;
import com.wang.wallpaper.service.UserService;
import com.wang.wallpaper.util.TokenUtil;
import com.wang.wallpaper.util.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 * @description 针对表【t_user】的数据库操作Service实现
 * @createDate 2022-09-29 16:49:44
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Page<User> selectList(User user, int pageSize, int currentPage) {
        Page<User> userPage = new Page<>();
        userPage.setSize(pageSize);
        userPage.setCurrent(currentPage);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Objects.nonNull(user.getType()), User::getType, user.getType())
                .eq(Objects.nonNull(user.getEnableFlag()), User::getEnableFlag, user.getEnableFlag())
                .like(StringUtils.isNotBlank(user.getNickname()), User::getNickname, user.getNickname())
                .like(StringUtils.isNotBlank(user.getUsername()), User::getUsername, user.getUsername())
                .orderByDesc(User::getCreateTime);
        userPage = this.page(userPage, wrapper);
        return userPage;
    }


    @Override
    public UserVO login(UserVO user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(User::getUsername, user.getUsername())
                .eq(User::getType, user.getType())
                .eq(User::getPassword, user.getPassword());
        List<User> userList = this.list(wrapper);
        if (CollectionUtil.isNotEmpty(userList)) {
            User result = userList.get(0);
            String token = TokenUtil.createToken(result);
            user.setToken(token);
            user.setUserId(result.getUserId());
            user.setNickname(result.getNickname());
            user.setHeadPortrait(result.getHeadPortrait());
            return user;
        } else {
            throw new RuntimeException("用户名或密码错误~");
        }
    }

    @Override
    public UserVO register(User user) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.lambda()
                .eq(User::getUsername, user.getUsername());
        List<User> userList = this.list(wrapper);
        if (CollectionUtil.isNotEmpty(userList)) {
            throw new RuntimeException("用户名已存在~");
        }
        this.save(user);
        String token = TokenUtil.createToken(user);
        UserVO userVO = new UserVO();
        userVO.setToken(token);
        userVO.setUserId(user.getUserId());
        userVO.setNickname(user.getNickname());
        userVO.setHeadPortrait(user.getHeadPortrait());
        return userVO;
    }

    @Override
    public void updatePassword(UserVO userVO) {
        User user = this.getById(userVO.getUserId());
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户不存在~");
        }
        if (!Objects.equals(user.getPassword(), userVO.getOldPassword())) {
            throw new RuntimeException("密码错误~");
        }
        if (!Objects.equals(user.getPassword(), userVO.getPassword())) {
            throw new RuntimeException("新密码与旧密码一致~");
        }
        user.setPassword(user.getPassword());
        this.updateById(user);
    }


    @Override
    public User userInfo(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.lambda()
                .eq(User::getUsername, username);
        List<User> userList = this.list(wrapper);
        if (CollectionUtil.isEmpty(userList)) {
            throw new RuntimeException("未找到用户~");
        }
        return userList.get(0);
    }

    @Override
    public Page<WallDTO> userWallList(int pageSize, int currentPage) {
        Page<WallDTO> wallPage = new Page<>();
        wallPage.setSize(pageSize);
        wallPage.setCurrent(currentPage);
        User user = UserThreadLocal.get();
        if (Objects.isNull(user)) {
            throw new NotLoginException(401, "登录已失效~");
        }
        wallPage = userMapper.userWallList(wallPage,user.getUserId());
        return wallPage;
    }

    @Override
    public Page<UserCommentVO> commentUserList(int pageSize, int currentPage) {
        Page<UserCommentVO> commentVOPage = new Page<>();
        commentVOPage.setSize(pageSize);
        commentVOPage.setCurrent(currentPage);
        User user = UserThreadLocal.get();
        if (Objects.isNull(user)) {
            throw new NotLoginException(401, "登录已失效~");
        }
        commentVOPage = userMapper.commentUserList(commentVOPage,user.getUserId());
        return commentVOPage;
    }
}




