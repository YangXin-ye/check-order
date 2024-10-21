package com.controller;

import com.entity.Classify;
import com.entity.User;
import com.service.ClassifyService;
import com.service.UserService;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @date 2024/10/18 12:50
 */
@RestController
@RequestMapping("classify")
@CrossOrigin
public class ClassifyController {


    @Autowired
    private ClassifyService classifyService;

    @Autowired
    private UserService userService;

    @PostMapping("insert")
    public R insert(@RequestBody Classify classify){
        User loginUser = userService.getLoginUser();
        classify.setUserId(loginUser.getId());
        classifyService.save(classify);
        return R.ok();
    }

    @PostMapping("delete")
    public R delete(@RequestBody Classify classify){
        classifyService.lambdaUpdate().eq(Classify::getId,classify.getId())
                .set(Classify::getDelete,1).update();
        return R.ok();
    }

    @PostMapping("update")
    public R update(@RequestBody Classify classify){
        classifyService.updateById(classify);
        return R.ok();
    }


    @GetMapping("select")
    public R select(){
        User user = userService.getLoginUser();
        List<Classify> list = classifyService.lambdaQuery()
                .eq(Classify::getDelete, 0) // 先过滤删除标记
                .and(wrapper -> wrapper
                        .eq(Classify::getUserId, user.getId())
                        .or().eq(Classify::getUserId, 0) // 这里确保是和 delete 条件一起
                )
                .list();
        return R.ok().put("data",list);
    }

}
