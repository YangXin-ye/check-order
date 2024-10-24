package com.controller;

import com.alibaba.fastjson.JSON;
import com.entity.Classify;
import com.entity.User;
import com.service.ClassifyService;
import com.service.UserService;
import com.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void syncRedisData(){
        User loginUser = userService.getLoginUser();
        R select = this.select();
        List<Classify> list = (List<Classify>) select.get("data");
        redisTemplate.opsForValue().set("calssify::data:"+loginUser.getId(), JSON.toJSONString(list));
    }

    @PostMapping("insert")
    public R insert(@RequestBody Classify classify){
        User loginUser = userService.getLoginUser();
        classify.setUserId(loginUser.getId());
        classifyService.save(classify);
        syncRedisData();
        return R.ok();
    }

    @PostMapping("delete")
    public R delete(@RequestBody Classify classify){
        classifyService.lambdaUpdate().eq(Classify::getId,classify.getId())
                .set(Classify::getDelete,1).update();
        syncRedisData();
        return R.ok();
    }

    @PostMapping("update")
    public R update(@RequestBody Classify classify){
        classifyService.updateById(classify);
        syncRedisData();
        return R.ok();
    }


    @GetMapping("select")
    public R select(){
        User user = userService.getLoginUser();
        String calssifyDataList = redisTemplate.opsForValue().get("calssify::data:" + user.getId());
        //如果redis中的key的数据是空，则进行查询数据并且返回
        if(StringUtils.isEmpty(calssifyDataList)){
            List<Classify> list = classifyService.lambdaQuery()
                    .eq(Classify::getDelete, 0) // 先过滤删除标记
                    .and(wrapper -> wrapper
                            .eq(Classify::getUserId, user.getId())
                            .or().eq(Classify::getUserId, 0) // 这里确保是和 delete 条件一起
                    )
                    .list();
            return R.ok().put("data",list);
        }else{
            List<Classify> list = JSON.parseArray(calssifyDataList, Classify.class);
            return R.ok().put("data",list);
        }

    }



}
