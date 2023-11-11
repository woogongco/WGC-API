package com.wgc.wgcapi.Homepage.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import com.wgc.wgcapi.Member.DTO.MemberDto;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseGuestBookDto {

    private Long id;
    private String content;
    private MemberDto writer;

    public ResponseGuestBookDto(GuestBook guestBook, Member writer) {
        this.id = guestBook.getId();
        this.content = guestBook.getContent();
        this.writer = new MemberDto(writer);
    }
}