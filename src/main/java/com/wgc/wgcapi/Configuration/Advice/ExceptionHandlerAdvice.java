package com.wgc.wgcapi.Configuration.Advice;
/*
Created on 2023/03/08 12:30 AM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = { IllegalAccessException.class, IllegalArgumentException.class })
    protected ResponseDto illegalAccessExceptionHandler(IllegalAccessException e, Object body, WebRequest request) {
        return new ResponseDto(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseDto CommonExceptionHandler(Exception e, Object body, WebRequest request) {
        e.printStackTrace();
        log.error("error message = {}", e.getMessage());
        return new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause() + "\n" + e.getMessage());
    }

    @ExceptionHandler(value = { NoHandlerFoundException.class })
    protected ResponseDto noHandlerFoundException(NoHandlerFoundException e, Object body, WebRequest request, HttpServletRequest servletRequest) {
        String uri = servletRequest.getRequestURI();
        String method = servletRequest.getMethod();
        return new ResponseDto(HttpStatus.BAD_REQUEST, method + " " + uri + " is not found !");
    }
}
