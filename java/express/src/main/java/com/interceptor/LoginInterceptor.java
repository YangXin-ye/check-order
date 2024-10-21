package com.interceptor;

import com.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @Author
 * @Create 2024-06-28-16:19
 * @Message
 **/
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        if("OPTIONS".equals(request.getMethod().toUpperCase())) {
            System.out.println("Method:OPTIONS");
            return true;
        }
        String token = request.getHeader("authorization");
        // 4. 判断令牌是否存在，不存在返回登录失败
        if(StringUtils.isEmpty(token)){
            throw new BizException(401,"token不存在");
        }
        String login = redisTemplate.opsForValue().get("loginToken::" + token);
        if(StringUtils.isEmpty(login)){
            throw new BizException(401,"用户未登录");
        }
        return true;
    }

}
