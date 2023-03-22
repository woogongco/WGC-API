package com.wgc.wgcapi.Member.DTO;
/*
Created on 2023/03/22 11:41 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.Utils.EncryptUtils;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.NoSuchAlgorithmException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpUserDto {

    private String name;
    private String mail;
    private String password;

    public Member asEntity() throws NoSuchAlgorithmException {
        String encrypted = EncryptUtils.encrypt(this.password);
        return new Member(this.name, this.mail, encrypted);
    }
}
