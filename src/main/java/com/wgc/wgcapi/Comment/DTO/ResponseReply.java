package com.wgc.wgcapi.Comment.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wgc.wgcapi.Comment.Entity.Comment;
import com.wgc.wgcapi.Member.DTO.MemberDto;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseReply {

    private Long replyId;
    private String content;

    private LocalDateTime registerDate;
    private LocalDateTime lastModifiedDate;
    private MemberDto writer;


    public ResponseReply(Long replyId, String content, LocalDateTime registerDate, LocalDateTime lastModifiedDate, Member writer) {
        this.replyId = replyId;
        this.content = content;
        this.registerDate = registerDate;
        this.lastModifiedDate = lastModifiedDate;
        this.writer = new MemberDto(writer);



    }

    public static ResponseReply of(Comment reply, Member getMember) {

        return new ResponseReply(reply.getId(), reply.getContent(), reply.getRegisterDate(),
                reply.getLastUpdateDate(), getMember);
    }
}
