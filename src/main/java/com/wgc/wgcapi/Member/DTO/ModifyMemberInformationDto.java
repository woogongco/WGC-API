package com.wgc.wgcapi.Member.DTO;
/*
Created on 2023/05/26 9:53 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ModifyMemberInformationDto {

    private String name;
    private String skill;
    private String color;
    private String introduction;
    private String password;
    private String github;
    @JsonProperty("profile_image")
    private String profileImage;
    @JsonProperty("phone_number")
    private String phoneNumber;
}
