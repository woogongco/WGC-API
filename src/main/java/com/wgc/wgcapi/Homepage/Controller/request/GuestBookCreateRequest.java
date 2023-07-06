package com.wgc.wgcapi.Homepage.Controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuestBookCreateRequest {

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public GuestBookCreateRequest(GuestBook guestBook) {
        this.content = guestBook.getContent();
    }
}
