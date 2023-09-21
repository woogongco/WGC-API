package com.wgc.wgcapi.Comment.Service;

import com.wgc.wgcapi.Comment.DTO.ResponseComment;
import com.wgc.wgcapi.Comment.DTO.ResponseComments;
import com.wgc.wgcapi.Comment.DTO.ResponseReply;
import com.wgc.wgcapi.Comment.Entity.Comment;
import com.wgc.wgcapi.Comment.Repository.CommentRepository;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReadService {
    private final CommentRepository commentRepository;

    public ResponseDto findComments(Long postId) {

        List<Comment> comments = commentRepository.findCommentsByPostId(postId);

        AtomicInteger repliesCount = new AtomicInteger();

        List<ResponseComment> commentResponses = comments.stream().map(comment -> {
            ResponseComment responseComment = convertToCommentResponse(comment);
            repliesCount.addAndGet(responseComment.getReplies().size());
            return responseComment;
        }).collect(Collectors.toList());
        int commentsCount = commentResponses.size();
        ResponseComments dto = new ResponseComments(commentResponses, commentsCount + repliesCount.get());
        return new ResponseDto(dto);
    }

    private ResponseComment convertToCommentResponse(Comment comment) {
        if (comment.isSoftRemoved()) {
            return ResponseComment.softRemovedOf(comment, convertToReplyResponses(comment, comment.getWriter()));
        }
        return ResponseComment.of(comment, comment.getWriter(), convertToReplyResponses(comment, comment.getWriter()));
    }

    private List<ResponseReply> convertToReplyResponses(Comment parent, Member getMember) {
        final List<Comment> replies = commentRepository.findRepliesByParent(parent);
        List<ResponseReply> replyResponses = new ArrayList<>();

        for (Comment reply : replies) {
            replyResponses.add(ResponseReply.of(reply, getMember));
        }
        return replyResponses;
    }
}
