package com.wang.wallpaper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.wallpaper.entity.Category;
import com.wang.wallpaper.mapper.CategoryMapper;
import com.wang.wallpaper.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author Administrator
* @description 针对表【t_category】的数据库操作Service实现
* @createDate 2022-09-29 16:49:44
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    /**
     * 查询类别  分页
     * @param category
     * @param pageSize  每页个数
     * @param currentPage  当前页
     * @return
     */
    @Override
    public Page<Category> selectList(Category category, int pageSize, int currentPage) {
        Page<Category> categoryPage = new Page<>();
        categoryPage.setSize(pageSize);
        categoryPage.setCurrent(currentPage);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Objects.nonNull(category.getEnableFlag()),Category::getEnableFlag,category.getEnableFlag())
                .like(StringUtils.isNotBlank(category.getCategoryName()),Category::getCategoryName,category.getCategoryName());
        categoryPage=  this.page(categoryPage,wrapper);
        return categoryPage;
    }
}




