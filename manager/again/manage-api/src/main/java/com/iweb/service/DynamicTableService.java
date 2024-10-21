package com.iweb.service;

import java.util.Map;

/**
 * @Author：
 * @Package：com.iweb.service
 * @Project：Test-Java
 * @name：DynamicTableService
 * @Date：2024/8/10 23:04
 * @Description:
 */
public interface DynamicTableService {
    Map<String, Object> list(Integer pageNum,Integer pageSize,String searchKey);
}
