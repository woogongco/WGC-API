package com.wgc.wgcapi.Configuration.Web.aop;
/*
Created on 2023/04/12 11:07 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Authentication.Service.AuthenticationService;
import com.wgc.wgcapi.Authentication.Service.JwtService;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class TokenAspect {

    private final JwtService jwtService;

    @Pointcut("execution(* com.wgc.wgcapi.*.Controller.*(..))")
    public void parseToken(JoinPoint joinPoint) {
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        String token = this.getTokenByHeader(request);

        if (Objects.isNull(token))
            return;

        Member member = jwtService.validate(token);
        request.setAttribute("claim", member);
    }

    private String getTokenByHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (Objects.nonNull(token) && token.startsWith("Bearer "))
            return token;

        return null;
    }
}
