package com.controller;

import com.service.DistrictService;
import com.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @date 2024/10/25 22:05
 */
@RestController
@RequestMapping("/district")
public class DistrictController {

    @Resource
    private DistrictService districtService;

    /**
     * 获取省份区域信息
     *
     * @param districtId 区域id
     * @param level      区域级别
     * @return
     */
    @GetMapping("/listDistrict")
    public R listDistrict(@RequestParam(value = "districtId", required = false) String districtId,
                          @RequestParam(value = "level", required = false) String level) {
        return R.ok().put("data",districtService.getDistrict(districtId, level));
    }


}
