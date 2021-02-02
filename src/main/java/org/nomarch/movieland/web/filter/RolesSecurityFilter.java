package org.nomarch.movieland.web.filter;

import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.entity.UserRole;
import org.nomarch.movieland.exception.InsufficientAccessRightsException;
import org.nomarch.movieland.security.SecurityService;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(filterName = "rolesSecurityFilter", urlPatterns = "/**")
public class RolesSecurityFilter implements Filter {
    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletContext servletContext = request.getServletContext();
        securityService = WebApplicationContextUtils.findWebApplicationContext(servletContext)
                .getBean(SecurityService.class);

        HttpServletRequest servletRequest = (HttpServletRequest) request;

        String requestURI = servletRequest.getRequestURI();
        String path = requestURI.substring(requestURI.indexOf("/v1") + 3);

        HttpMethod method = HttpMethod.valueOf(servletRequest.getMethod());

        if (method == HttpMethod.GET || path.equals("/login")) {
            chain.doFilter(request, response);
        }

        String uuid = servletRequest.getHeader("uuid");
        if (uuid != null) {
            User user = securityService.findUserByUUIDToken(uuid);
            if (user.getRole() == UserRole.ADMIN) {
                chain.doFilter(request, response);
            }

            if (user.getRole() == UserRole.USER) {
                if (path.equals("/review") && method == HttpMethod.POST) {
                    chain.doFilter(request, response);
                }
            }
        }

        throw new InsufficientAccessRightsException("Request sender don't have access rights to access url " + method.toString() + " " + requestURI);
    }

    @Override
    public void destroy() {

    }
}
