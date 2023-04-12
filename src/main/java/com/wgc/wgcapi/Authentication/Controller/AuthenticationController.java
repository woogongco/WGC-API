package com.wgc.wgcapi.Authentication.Controller;
/*
Created on 2023/02/21 11:22 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Authentication.Service.AuthenticationService;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/validate")
    public ResponseDto getUserInformation(@RequestBody Map<String, String> param) {
        return this.authenticationService.validateToken(param);
    }
}
