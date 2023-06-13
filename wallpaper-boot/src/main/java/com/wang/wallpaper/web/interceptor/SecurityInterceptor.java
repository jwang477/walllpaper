package com.wang.wallpaper.web.interceptor;


import com.wang.wallpaper.constants.anno.UserAuth;
import com.wang.wallpaper.constants.anno.VisitAuth;
import com.wang.wallpaper.constants.exception.NotLoginException;
import com.wang.wallpaper.entity.User;
import com.wang.wallpaper.util.TokenUtil;
import com.wang.wallpaper.util.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


@Slf4j
@Component
public class SecurityInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Class<?> clazz = handlerMethod.getBeanType();
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(UserAuth.class) || clazz.isAnnotationPresent(UserAuth.class)) {
            checkLoginStatus(request);
        } else if (method.isAnnotationPresent(VisitAuth.class) || clazz.isAnnotationPresent(VisitAuth.class)) {
            checkVisitStatus(request);
        }

        return true;
    }

    private void checkLoginStatus(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            throw new NotLoginException(401,"登录已失效~");
        }
        User user = TokenUtil.parseToken(token);
        UserThreadLocal.put(user);
    }

    private void checkVisitStatus(HttpServletRequest request) {
        String token = request.getHeader("token");
        try {
            User user = TokenUtil.parseToken(token);
            UserThreadLocal.put(user);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        UserThreadLocal.remove();
    }
}
