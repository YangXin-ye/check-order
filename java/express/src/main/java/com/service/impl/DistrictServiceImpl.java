package com.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.DistrictMapper;
import com.dao.UserMapper;
import com.entity.District;
import com.entity.User;
import com.service.DistrictService;
import com.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @date 2024/10/25 22:09
 */
@Service
public class DistrictServiceImpl extends ServiceImpl<DistrictMapper, District>
        implements DistrictService {
    /**
     * 获取省份区域信息
     *
     * @param districtId 区域id
     * @param level      区域级别
     */
    @Override
    public List<District> getDistrict(String districtId, String level) {
        if (StrUtil.isAllBlank(districtId, level)) {
            throw new RuntimeException("id和level不能同时为空");
        }
        // 如果传递id，则查询该id下的下一级
        if (StrUtil.isNotBlank(districtId)) {
            return this.list(Wrappers.<District>lambdaQuery().eq(District::getParentId, districtId));
        }
        // 如果传递level，则查询该level对应的数据
        return this.list(Wrappers.<District>lambdaQuery().eq(District::getLevel, level));
    }
}
