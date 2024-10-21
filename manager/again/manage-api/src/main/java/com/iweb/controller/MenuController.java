package com.iweb.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.iweb.config.Result;
import com.iweb.entity.Menu;
import com.iweb.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;


    @RequestMapping("/list")
    public Result getList(){
        List<Menu> list = menuService.list();
        //获取全部一级
        List<Menu> parentMenu = list.stream().filter(item -> item.getParent().intValue()==0).collect(Collectors.toList());
        //组装一级中的所有二级
        for (Menu menu : parentMenu) {
            List<Menu> children = list.stream().filter(item -> item.getParent().equals(menu.getId())).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(children)){
                menu.setChildren(children);
            }
        }
        return Result.success(parentMenu);
    }

}
