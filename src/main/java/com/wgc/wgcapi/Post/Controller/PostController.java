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
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseDto getPostsByCategory(@RequestParam(name = "limit", defaultValue = "5") Long limit) {
        return this.postService.getPostByCategory(limit);
    }

    @PostMapping
    @RequireToken
    public ResponseDto writePost(@RequestBody WritePostDto dto, HttpServletRequest request) {
        return this.postService.writePost(request, dto);
    }

    @PatchMapping("/{id}")
    @RequireToken
    public ResponseDto editPost(@RequestBody EditPostDto dto, HttpServletRequest request, @PathVariable(name = "id") Long id) {
        return this.postService.editPost(request, dto, id);
    }

    @DeleteMapping("/{id}")
    @RequireToken
    public ResponseDto deletePost(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        return this.postService.deletePost(request, id);
    }

    @GetMapping("/{id}")
    public ResponseDto getPost(@PathVariable(name = "id") Long id) {
        return this.postService.getPost(id);
    }

    @GetMapping("/category")
    public ResponseDto getCategories() {
        return this.postService.getCategories();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseDto getPostList(@PathVariable(name = "categoryId") Long categoryId, @RequestParam(name = "limit", required = false, defaultValue = "10") Long limit) {
        return this.postService.getPostList(categoryId, limit);
    }

    @PostMapping("/{id}/like")
    @RequireToken
    public ResponseDto likePost(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        return this.postService.likePost(request, id);
    }

    @PostMapping("/{id}/dislike")
    @RequireToken
    public ResponseDto dislikePost(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        return this.postService.dislikePost(request, id);
    }

    @GetMapping(value = { "/homepage", "/homepage/{id}" })
    public ResponseDto getPostsByUserId(@PathVariable(name = "id", required = false) Long userId, HttpServletRequest request) throws IllegalAccessException {
        return this.postService.getPostsByUserId(request, userId);
    }
}
