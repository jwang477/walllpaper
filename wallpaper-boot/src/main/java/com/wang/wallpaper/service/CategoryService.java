package com.wang.wallpaper.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wallpaper.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【t_category】的数据库操作Service
* @createDate 2022-09-29 16:49:44
*/
public interface CategoryService extends IService<Category> {

    Page<Category> selectList(Category category, int pageSize, int currentPage);
}
