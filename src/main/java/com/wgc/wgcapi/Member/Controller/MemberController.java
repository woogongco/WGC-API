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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService service;

    @PutMapping
    public ResponseDto signUp(@RequestBody SignUpUserDto dto) {
        return service.signUp(dto);
    }

    @PostMapping("/sign")
    public ResponseDto signIn(@RequestBody SignInUserDto dto) {
        return service.signIn(dto);
    }
}
