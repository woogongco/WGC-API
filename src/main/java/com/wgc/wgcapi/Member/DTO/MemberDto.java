package com.wgc.wgcapi.Member.DTO;
/*
Created on 2023/03/09 10:40 PM In Intelli J IDEA 
by jeon-wangi
*/

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {

    private Long id;
    private String name;
    private String mail;
    private String password;
    private String permission;
    private LocalDateTime registerDateTime;
}
