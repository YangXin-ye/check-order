package com.iweb.controller;

import com.iweb.config.Result;
import com.iweb.entity.Classify;
import com.iweb.entity.ListUserPageReq;
import com.iweb.entity.User;
import com.iweb.service.ClassifyService;
import com.iweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @date 2024/10/18 12:50
 */
@RestController
@RequestMapping("classify")
public class ClassifyController {



    @Autowired
    private ClassifyService classifyService;

    /**
     * 分页查询所有数据
     *
     * @param listUserPageReq 请求体
     * @param pageNum         当前页
     * @param pageSize        每页大小
     */
    @GetMapping("/list")
    public Result classifyList(ListUserPageReq listUserPageReq,
                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {

        return Result.success(classifyService.classifyList(pageNum,pageSize,listUserPageReq));
    }

    @PostMapping("/insert")
    public Result insertClassify(@RequestBody Classify classify) {
        return Result.success(classifyService.insertClassify(classify));
    }

    @GetMapping("/delete")
    public Result deleteClassify(@RequestParam Integer id) {
        return Result.success(classifyService.deleteClassify(id));
    }

    @PostMapping("/update")
    public Result updateClassify(@RequestBody Classify classify) {
        return Result.success(classifyService.updateClassify(classify));
    }


}
