package zq.bim.Interceptor;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import zq.bim.constant.REQUEST_MESS;
import zq.bim.entity.dto.UserLoginOKDefinition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/***
 * 请求拦截器
 * 获取到这个登录的用户信息，如果用户没有登录也可以直接放行
 * @author 刘梓江
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取Cookie中的信息用户登录标识
        String loginToken = request.getHeader("token");
        if (!StringUtils.isEmpty(loginToken)) {
            // 不等于空我们就去Redis中查询用户信息
            String userLoginContent = redisTemplate.opsForValue().get(loginToken);
            if (!StringUtils.isEmpty(userLoginContent)) {
                // 用户登录了 就将数据存入Request
                request.setAttribute(REQUEST_MESS.USER_INFO.name(), JSON.parseObject(userLoginContent, UserLoginOKDefinition.class));
            }
        }
        return true;
    }
}
