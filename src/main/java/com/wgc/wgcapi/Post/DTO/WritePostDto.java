package com.wgc.wgcapi.Post.DTO;
/*
Created on 2023/04/12 11:46 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Post.Entity.Category;
import com.wgc.wgcapi.Post.Entity.Post;
import lombok.Data;

@Data
public class WritePostDto {

    private String title;
    private String content;
    private Long categoryId;

    public Post asPostEntity(Member member, Category category){
        return new Post(member, category, this);
    }
}
