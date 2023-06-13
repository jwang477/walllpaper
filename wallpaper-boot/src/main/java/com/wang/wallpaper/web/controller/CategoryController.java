package com.wang.wallpaper.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.wallpaper.constants.R;
import com.wang.wallpaper.constants.anno.UserAuth;
import com.wang.wallpaper.entity.Category;
import com.wang.wallpaper.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jian.wang@zone-cloud.com
 * @date 2022-10-10 10:20
 */
@RestController
@RequestMapping("category")
public class CategoryController extends BaseController {


    @Autowired
    private CategoryService categoryService;

    /**
     * 查询类别
     * @param category
     * @param pageSize
     * @param currentPage
     * @return
     */
    @GetMapping
    public R list(Category category,
                  @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                  @RequestParam(value = "currentPage", defaultValue = "1") int currentPage) {
        Page<Category> categoryList = categoryService.selectList(category, pageSize, currentPage);
        return R.Page(categoryList);
    }
    @GetMapping("all")
    public R all() {
        List<Category> categoryList = categoryService.list();
        return R.success(categoryList);
    }
    /**
     * 类别保存  --  批量
     * @param categoryList
     * @return
     */
    @PostMapping
    @UserAuth
    public R save(@RequestBody List<Category> categoryList) {

        categoryService.saveOrUpdateBatch(categoryList);

        return R.success(categoryList);
    }

    /**
     * 批量删除
     * @param deleteList
     * @return
     */
    @DeleteMapping
    @UserAuth
    public R delete(@RequestBody List<Long> deleteList) {

        categoryService.removeBatchByIds(deleteList);

        return R.ok();
    }

}
