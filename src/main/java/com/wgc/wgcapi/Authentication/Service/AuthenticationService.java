package com.wgc.wgcapi.Authentication.Service;
/*
Created on 2023/03/09 10:20 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;

    public Member validateToken(String token) {
        return this.jwtService.validate(token);
    }

}
