package com.wgc.wgcapi.Member.DTO;
/*
Created on 2023/03/09 10:40 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {

    private Long id;
    private String name;
    private String mail;
    @JsonIgnore
    private String password;
    private String permission;
    private String skil;
    private String github;
    private String introduction;
    private String color;
    private LocalDateTime registerDateTime;
    private String profileImage;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.mail = member.getMail();
        this.registerDateTime = member.getRegisterDateTime();
        this.skil = member.getSkil();
        this.introduction = member.getIntroduction();
        this.color = member.getColor();
        this.github = member.getGithub();
        this.profileImage = member.getProfileImage();
    }
}
