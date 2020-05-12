package com.empirefree.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empirefree.gulimall.product.entity.CategoryEntity;
import com.empirefree.gulimall.product.service.CategoryService;
import com.empirefree.common.utils.PageUtils;
import com.empirefree.common.utils.R;



/**
 * 商品三级分类
 *
 * @author Empirefree
 * @email skt.hyq@gmail.com
 * @date 2020-05-08 11:19:19
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * 列表-分类以及子分类
     */
    @RequestMapping("/list/tree")
    public R list(){
        List<CategoryEntity> entities = categoryService.listWithTree();
        return R.ok().put("data", entities);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
//		categoryService.removeByIds(Arrays.asList(catIds));
        
		categoryService.removeMenuIds(Arrays.asList(catIds));
        return R.ok();
    }

}
