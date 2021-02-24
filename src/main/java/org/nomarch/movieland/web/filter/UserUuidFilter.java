package org.nomarch.movieland.web.filter;

import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.entity.UserRole;
import org.nomarch.movieland.exception.InsufficientAccessRightsException;
import org.nomarch.movieland.security.SecurityService;
import org.nomarch.movieland.security.UserHolder;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "rolesSecurityFilter", urlPatterns = "/*")
public class UserUuidFilter implements Filter {
    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext servletContext = filterConfig.getServletContext();
        securityService = WebApplicationContextUtils.findWebApplicationContext(servletContext)
                .getBean(SecurityService.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;

        String uuid = servletRequest.getHeader("uuid");
        if (uuid != null) {
            User user = securityService.findUserByUUIDToken(uuid);
            UserHolder.setUser(user);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
