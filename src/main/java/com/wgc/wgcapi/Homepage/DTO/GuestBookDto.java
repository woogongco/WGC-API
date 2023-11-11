package com.wgc.wgcapi.Homepage.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuestBookDto {

    private String content;

    public GuestBook asGuestBookEntity(Member writerMember) {
        return new GuestBook(writerMember, this);
    }
}
