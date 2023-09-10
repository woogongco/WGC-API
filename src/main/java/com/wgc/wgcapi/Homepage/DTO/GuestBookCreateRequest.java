package com.wgc.wgcapi.Homepage.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuestBookCreateRequest {

    private String content;

    public GuestBookCreateRequest(GuestBook guestBook) {
        this.content = guestBook.getContent();
    }
}
