package com.iweb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iweb.entity.Classify;
import com.iweb.entity.ListUserPageReq;
import com.iweb.entity.User;

/**
*
* @description 针对表【t_classify(收支分类)】的数据库操作Service
* @createDate 2024-10-17 21:44:11
*/
public interface ClassifyService extends IService<Classify> {

    Page<Classify> classifyList(Integer pageNum, Integer pageSize, ListUserPageReq listUserPageReq);

    Boolean insertClassify(Classify classify);

    Boolean deleteClassify(Integer classifyId);

    Boolean updateClassify(Classify classify);
}
