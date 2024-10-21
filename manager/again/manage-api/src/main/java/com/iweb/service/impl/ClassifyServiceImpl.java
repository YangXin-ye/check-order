package com.iweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.iweb.entity.Classify;
import com.iweb.entity.ListUserPageReq;
import com.iweb.entity.User;
import com.iweb.mapper.ClassifyMapper;
import com.iweb.service.ClassifyService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
*
* @description 针对表【t_classify(收支分类)】的数据库操作Service实现
* @createDate 2024-10-17 21:44:11
*/
@Service
public class ClassifyServiceImpl extends ServiceImpl<ClassifyMapper, Classify>
    implements ClassifyService {

    @Override
    public Page<Classify> classifyList(Integer pageNum, Integer pageSize, ListUserPageReq listUserPageReq) {
        LambdaQueryWrapper<Classify> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtil.isNotEmpty(listUserPageReq.getName())){
            lambdaQueryWrapper.like(Classify::getName,listUserPageReq.getName());
        }
        lambdaQueryWrapper.eq(Classify::getDelete,0);
        Page<Classify> page = new Page<>(pageNum, pageSize);
        Page<Classify> resultPage = baseMapper.selectPage(page, lambdaQueryWrapper);
        return resultPage;
    }

    @Override
    public Boolean insertClassify(Classify classify) {
        if(classify == null){
            throw new RuntimeException("分类信息不能为空");
        }
        if(StringUtil.isEmpty(classify.getName())){
            throw new RuntimeException("分类名称不能为空");
        }
        if(null == classify.getType()){
            throw new RuntimeException("分类类型不能为空");
        }
        classify.setUserId(0);
        classify.setCreateTime(new Date());
        classify.setDelete(0);
        return save(classify);
    }

    @Override
    public Boolean deleteClassify(Integer classifyId) {
        if(classifyId == null){
            throw new RuntimeException("分类Id不能为空");
        }
        Classify classify = this.getById(classifyId);
        if(classify==null){
            throw new RuntimeException("分类不存在");
        }
        Classify upClassify = new Classify();
        upClassify.setDelete(1);
        upClassify.setId(classifyId);
        return updateById(upClassify);
    }

    @Override
    public Boolean updateClassify(Classify classify) {
        if(classify == null){
            throw new RuntimeException("分类Id不能为空");
        }
        if(classify.getId() == null){
            throw new RuntimeException("分类Id不能为空");
        }
        Classify sClassify = this.getById(classify.getId());
        if(sClassify==null){
            throw new RuntimeException("分类不存在");
        }
        if(StringUtil.isEmpty(classify.getName())){
            throw new RuntimeException("分类名称不能为空");
        }
        if(null == classify.getType()){
            throw new RuntimeException("分类类型不能为空");
        }
        return updateById(classify);
    }
}




