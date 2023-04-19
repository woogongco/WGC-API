package com.wgc.wgcapi.Post.DTO;
/*
Created on 2023/04/19 10:35 PM In Intelli J IDEA 
by jeon-wangi
*/

import lombok.Data;

@Data
public class EditPostDto {

    private Long id;
    private String title;
    private String content;
    private Long categoryId;

}
