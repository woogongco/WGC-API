package com.wgc.wgcapi.Post.DTO;
/*
Created on 2023/04/19 11:35 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wgc.wgcapi.Member.DTO.MemberDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Post.Entity.Category;
import com.wgc.wgcapi.Post.Entity.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePostDto {

    private Long id;
    private String title;
    private String content;
    private Long like;
    private Long view;
    private LocalDateTime registerDate;
    private LocalDateTime lastModifiedDate;
    private MemberDto writer;
    private Category category;


    public ResponsePostDto(Post post, Member writer) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.like = post.getLike();
        this.view = post.getView();
        this.registerDate = post.getRegisterDate();
        this.lastModifiedDate = post.getLastUpdateDate();
        this.writer = new MemberDto(writer);
    }
}
