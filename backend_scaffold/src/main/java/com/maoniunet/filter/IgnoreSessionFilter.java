package com.maoniunet.filter;

import com.maoniunet.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Order(value = 10)
@Component
public class IgnoreSessionFilter extends OncePerRequestFilter {

    final static Set<IgnoreSessionMapping> IGNORE_PATHS = new ConcurrentSkipListSet<>();
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        for (IgnoreSessionMapping ignorePath : IGNORE_PATHS) {
            String requestURI = request.getRequestURI();
            if (requestURI.endsWith("/")) {
                requestURI = requestURI.substring(0, requestURI.length() - 1);
            }
            boolean pathMatch = antPathMatcher.match(toAntPathPattern(ignorePath.getPath()), requestURI);
            boolean methodMatch = request.getMethod().equals(ignorePath.getRequestMethod().name());
            if (pathMatch && methodMatch) {
                WebUtils.setSessionAttribute(request, Constant.IGNORE_SESSION, true);
                break;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String toAntPathPattern(String requestPath) {
        String regex = "\\{+\\w+\\}+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(requestPath);

        while (matcher.find()) {
            requestPath = requestPath.replace(matcher.group(), "*");
        }

        return requestPath;
    }

    @Data
    @AllArgsConstructor
    static class IgnoreSessionMapping implements Comparable<IgnoreSessionMapping> {
        private String path;
        private RequestMethod requestMethod;


        @Override
        public int compareTo(IgnoreSessionMapping o) {
            return this.getPath().compareTo(o.getPath());
        }
    }
}
