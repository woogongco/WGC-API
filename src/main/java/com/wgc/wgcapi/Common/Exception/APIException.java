package com.wgc.wgcapi.Common.Exception;

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class APIException extends RuntimeException {
    private final ResponseDto responseDto;

    public APIException(ResponseDto responseDto) {
        super(responseDto.getMessage());
        this.responseDto = responseDto;
    }
}