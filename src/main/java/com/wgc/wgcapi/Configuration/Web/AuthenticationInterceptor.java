package com.wgc.wgcapi.Configuration.Web;
/*
Created on 2023/03/08 12:15 AM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.Annotations.RequireToken;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Optional;

@Configuration
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            this.validate((HandlerMethod) handler, request);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private <A extends Annotation> A getAnnotation(HandlerMethod handlerMethod, Class<A> type) {
        A annotation = handlerMethod.getMethodAnnotation(type);
        return Optional
                .ofNullable(annotation)
                .orElse(handlerMethod.getBeanType().getAnnotation(type));
    }

    private void validate(HandlerMethod handler, HttpServletRequest request) throws IllegalAccessException {
        RequireToken annotation = this.getAnnotation(handler, RequireToken.class);
        if (isAnnotationExists(annotation) && request.getHeader("Authorization") == null)
            throw new IllegalAccessException("Require Token is missing !");
    }

    private boolean isAnnotationExists(RequireToken annotation) {
        return ! ObjectUtils.isEmpty(annotation);
    }
}
