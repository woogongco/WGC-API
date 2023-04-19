package com.wgc.wgcapi.Post.Controller;
/*
Created on 2023/02/21 11:25 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.Annotations.RequireToken;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Post.DTO.EditPostDto;
import com.wgc.wgcapi.Post.DTO.WritePostDto;
import com.wgc.wgcapi.Post.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {

    private final PostService postService;

    @PostMapping
    @RequireToken
    public ResponseDto writePost(@RequestBody WritePostDto dto, HttpServletRequest request) {
        return this.postService.writePost(request, dto);
    }

    @PostMapping("/edit")
    @RequireToken
    public ResponseDto editPost(@RequestBody EditPostDto dto, HttpServletRequest request) {
        return this.postService.editPost(request, dto);
    }
}
