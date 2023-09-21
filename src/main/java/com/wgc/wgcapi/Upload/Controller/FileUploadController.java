package com.wgc.wgcapi.Upload.Controller;
/*
Created on 2023/08/21 9:23 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.Annotations.RequireToken;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Upload.Service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/upload")
public class FileUploadController {

    private final FileUploadService service;

    @PostMapping
    @RequireToken
    public ResponseDto upload(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
        return service.upload(request, response, file);
    }

}
