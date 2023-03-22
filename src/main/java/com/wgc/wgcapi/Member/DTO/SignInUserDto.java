package com.wgc.wgcapi.Member.DTO;
/*
Created on 2023/03/23 12:20 AM In Intelli J IDEA 
by jeon-wangi
*/

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInUserDto {

    private String mail;
    private String password;

}
