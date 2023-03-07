package com.wgc.wgcapi.Common.DTO;
/*
Created on 2023/03/08 12:33 AM In Intelli J IDEA 
by jeon-wangi
*/

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {

    private int status;
    private String message;
    private Object data;

    public ResponseDto() {
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }

    public ResponseDto(HttpStatus httpStatus, Exception e) {
        this.status = httpStatus.value();
        this.message = e.getMessage();
    }

    public ResponseDto(Object data) {
        this.status = HttpStatus.OK.value();
        this.data = data;
    }
}
