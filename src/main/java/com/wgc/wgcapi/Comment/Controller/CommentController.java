package com.wgc.wgcapi.Comment.Controller;

import com.wgc.wgcapi.Comment.DTO.RequestComment;
import com.wgc.wgcapi.Comment.DTO.RequestReply;
import com.wgc.wgcapi.Comment.Service.CommentReadService;
import com.wgc.wgcapi.Comment.Service.CommentWriteService;
import com.wgc.wgcapi.Common.Annotations.RequireToken;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentWriteService commentWriteService;
    private final CommentReadService commentReadService;

    @PostMapping("/{postId}")
    @RequireToken
    public ResponseDto addComment(@PathVariable(name = "postId") Long postId,
                                  @RequestBody RequestComment dto,
                                  HttpServletRequest request) {
        return this.commentWriteService.addComment(dto, request, postId);
    }

    @PostMapping("/{commentId}/reply")
    @RequireToken
    public ResponseDto addReply(HttpServletRequest request,
                                @PathVariable(name = "commentId") Long commentId,
                                @RequestBody RequestReply dto) {
        return this.commentWriteService.addReply(dto, request, commentId);

    }

    @PutMapping("/{commentId}")
    @RequireToken
    public ResponseDto editComment(@PathVariable(name = "commentId") Long commentId,
                                   @RequestBody RequestComment dto,
                                   HttpServletRequest request) {
        return this.commentWriteService.editComment(dto, request, commentId);
    }

    @GetMapping("/{postId}")
    public ResponseDto findComments(@PathVariable(name = "postId") Long postId) {
        return this.commentReadService.findComments(postId);

    }

    @DeleteMapping("/{commentId}")
    @RequireToken
    public ResponseDto deleteComment(@PathVariable(name = "commentId") Long commentId,
                                     HttpServletRequest request) {
        return this.commentWriteService.deleteComment(request, commentId);
    }
}
