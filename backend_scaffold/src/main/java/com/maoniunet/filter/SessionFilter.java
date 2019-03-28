package com.maoniunet.filter;


import com.maoniunet.constant.Constant;
import com.maoniunet.entity.UserEntity;
import com.maoniunet.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Order(20)
@Component
public class SessionFilter extends OncePerRequestFilter {

    @Resource
    private UserMapper userMapper;

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Boolean ignoreSession = (Boolean) WebUtils.getSessionAttribute(request, Constant.IGNORE_SESSION);
        if (Boolean.TRUE.equals(ignoreSession)) {
            log.info("{} {} ignore session!", request.getMethod(), request.getRequestURI());
            filterChain.doFilter(request, response);
            WebUtils.setSessionAttribute(request, Constant.IGNORE_SESSION, null);
            return;
        }

        UserEntity user = (UserEntity) WebUtils.getSessionAttribute(request, Constant.CURRENT_USER);
        if (null == user) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }

    public static String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
