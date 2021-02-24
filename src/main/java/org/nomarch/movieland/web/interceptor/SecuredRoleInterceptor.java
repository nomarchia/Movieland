package org.nomarch.movieland.web.interceptor;

import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.entity.UserRole;
import org.nomarch.movieland.exception.InsufficientAccessRightsException;
import org.nomarch.movieland.security.UserHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecuredRoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Secured secured = handlerMethod.getMethodAnnotation(Secured.class);

        if (secured.value() == UserRole.GUEST) {
            return true;
        }

        User user = UserHolder.getUser();
        if (user != null && user.getRole().getRoleNum() >= secured.value().getRoleNum()) {
            return true;
        }

        throw new InsufficientAccessRightsException("User " + user.getNickname() + "don't have permission to access url: " + request.getRequestURI());
    }
}
