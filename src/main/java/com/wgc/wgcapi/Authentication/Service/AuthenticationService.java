package com.wgc.wgcapi.Authentication.Service;
/*
Created on 2023/03/09 10:20 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;

    public String getAuthenticationToken(Member member) {
        return jwtService.generate(member);
    }

    public ResponseDto validateToken(Map<String, String> param) throws IllegalAccessException {
        String token = param.get("token");
        if (Objects.isNull(token))
            return new ResponseDto(HttpStatus.BAD_REQUEST, "Request parameter token is missing !");

        Member member = this.jwtService.validate(token);
        return new ResponseDto(member.asDto());
    }

}
