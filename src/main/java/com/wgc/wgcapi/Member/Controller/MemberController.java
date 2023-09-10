package com.wgc.wgcapi.Member.Controller;
/*
Created on 2023/02/21 11:25 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.Annotations.RequireToken;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.DTO.ModifyMemberInformationDto;
import com.wgc.wgcapi.Member.DTO.SignInUserDto;
import com.wgc.wgcapi.Member.DTO.SignUpUserDto;
import com.wgc.wgcapi.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

    @PostMapping("/introduction")
    @RequireToken
    public ResponseDto modifyIntroduction(HttpServletRequest request, @RequestBody Map<String, String> param) {
        return this.service.modifyIntroduction(request, param);
    }

    @GetMapping("/my-info")
    @RequireToken
    public ResponseDto getMyInformation(HttpServletRequest request) {
        return this.service.getMyInformation(request);
    }

    @PostMapping("/information")
    @RequireToken
    public ResponseDto modifyInformation(HttpServletRequest request, @RequestBody ModifyMemberInformationDto dto) {
        return this.service.modifyInformation(request, dto);
    }
}
