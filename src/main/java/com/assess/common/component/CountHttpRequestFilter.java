package com.assess.common.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
@Component
public class CountHttpRequestFilter extends OncePerRequestFilter {

    private final AtomicLong counter = new AtomicLong(0);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if(!request.getServletPath().equals("/v1/question/forth")){
            counter.incrementAndGet();
        }
        filterChain.doFilter(request, response);
    }

    public long getCount() {
        return counter.get();
    }
}
