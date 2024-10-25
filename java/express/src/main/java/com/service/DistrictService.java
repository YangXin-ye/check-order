package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.District;

import java.util.List;

/**
 * @date 2024/10/25 22:07
 */
public interface DistrictService extends IService<District> {


    /**
     * 获取省份区域信息
     *
     * @param districtId 区域id
     * @param level      区域级别
     */
    List<District> getDistrict(String districtId, String level);
}
