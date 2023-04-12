package com.wgc.wgcapi.Post.DTO;
/*
Created on 2023/04/12 11:46 PM In Intelli J IDEA 
by jeon-wangi
*/

import lombok.Data;

@Data
public class WritePostDto {

    private String title;
    private String contents;
    private String category;
}
