package com.wgc.wgcapi.Homepage.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String writer;

    public GuestBookResponse(Long guestBookId,  String writer,String content) {
        this.guestBookId = guestBookId;
        this.writer = writer;
        this.content = content;

    }

}