package com.wgc.wgcapi.Configuration.Advice;
/*
Created on 2023/03/08 12:30 AM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = { IllegalAccessException.class })
    protected ResponseDto illegalAccessExceptionHandler(Exception e, Object body, WebRequest request) {
        return new ResponseDto(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(value = { NoHandlerFoundException.class })
    protected ResponseDto noHandlerFoundException(Exception e, Object body, WebRequest request, HttpServletRequest servletRequest) {
        String uri = servletRequest.getRequestURI();
        String method = servletRequest.getMethod();
        return new ResponseDto(HttpStatus.BAD_REQUEST, method + " " + uri + " is not found !");
    }
}
