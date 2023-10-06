package com.wgc.wgcapi.Homepage.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import com.wgc.wgcapi.Member.DTO.MemberDto;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuestBookResponse {

    private Long guestBookId;
    private String content;
    private MemberDto writer;

    public GuestBookResponse(GuestBook guestBook) {
        this.guestBookId = guestBook.getId();
        this.writer = new MemberDto(guestBook.getWriterMember());
        this.content = guestBook.getContent();
    }
}