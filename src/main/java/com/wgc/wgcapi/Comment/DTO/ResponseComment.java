package com.wgc.wgcapi.Comment.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wgc.wgcapi.Comment.Entity.Comment;
import com.wgc.wgcapi.Member.DTO.MemberDto;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseComment {

    List<ResponseReply> replies;
    private Long commentId;
    private String content;
    private LocalDateTime registerDate;
    private LocalDateTime lastModifiedDate;
    private MemberDto writer;

    public ResponseComment(Long commentId, String content, LocalDateTime registerDate, LocalDateTime lastModifiedDate, Member writer, List<ResponseReply> replies) {
        this.commentId = commentId;
        this.content = content;
        this.registerDate = registerDate;
        this.lastModifiedDate = lastModifiedDate;
        this.writer = new MemberDto(writer);
        this.replies = replies;
    }

    public ResponseComment(Long commentId, List<ResponseReply> replies) {
        this.commentId = commentId;
        this.replies = replies;
    }

    public static ResponseComment softRemovedOf(Comment comment, List<ResponseReply> replyResponses) {
        return new ResponseComment(comment.getId(), replyResponses);
    }

    public static ResponseComment of(Comment comment, Member writer, List<ResponseReply> replyResponses) {
        return new ResponseComment(comment.getId(), comment.getContent(), comment.getRegisterDate(),
                comment.getLastUpdateDate(), writer, replyResponses);
    }
}
