package com.wgc.wgcapi.Member.Controller;
/*
Created on 2023/02/21 11:25 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.DTO.SignInUserDto;
import com.wgc.wgcapi.Member.DTO.SignUpUserDto;
import com.wgc.wgcapi.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService service;

    @PostMapping
    public ResponseDto signUp(@RequestBody SignUpUserDto dto) {
        return service.signUp(dto);
    }

    @PostMapping("/sign")
    public ResponseDto signIn(@RequestBody SignInUserDto dto) {
        return service.signIn(dto);
    }
}
