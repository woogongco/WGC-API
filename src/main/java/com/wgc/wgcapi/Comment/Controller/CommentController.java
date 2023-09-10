package com.wgc.wgcapi.Comment.Controller;

import com.wgc.wgcapi.Comment.DTO.RequestComment;
import com.wgc.wgcapi.Comment.DTO.RequestReply;
import com.wgc.wgcapi.Comment.Service.CommentWriteService;
import com.wgc.wgcapi.Common.Annotations.RequireToken;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    private final CommentWriteService commentService;


    @PostMapping("/posts/{postId}/comments")
    @RequireToken
    public ResponseDto addComment(@PathVariable(name = "postId") Long postId, @RequestBody RequestComment dto, HttpServletRequest request) {
        return this.commentService.addComment(dto,request, postId);
    }

    @PostMapping("/comments/{commentId}/reply")
    @RequireToken
    public ResponseDto addReply( HttpServletRequest request,
                                 @PathVariable(name = "commentId") Long commentId,
                                 @RequestBody RequestReply dto) {

        return this.commentService.addReply(dto,request, commentId);

    }



    @PatchMapping("/comments/{commentId}")
    @RequireToken
    public ResponseDto editComment(@PathVariable(name = "commentId") Long commentId, @RequestBody RequestComment dto, HttpServletRequest request) {
        return this.commentService.editComment(dto, request, commentId);
    }



    @GetMapping("/posts/{postId}/comments")
    public ResponseDto findComments(
                                     @PathVariable(name = "postId") Long postId
                                     ) {

        return this.commentService.findComments(postId);

    }


    @DeleteMapping("/comments/{commentId}")
    @RequireToken
    public ResponseDto deleteComment(@PathVariable(name = "commentId") Long commentId, HttpServletRequest request) {
        return this.commentService.deleteComment(request, commentId);
    }



}
