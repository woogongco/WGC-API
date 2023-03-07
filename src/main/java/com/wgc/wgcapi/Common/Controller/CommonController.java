package com.wgc.wgcapi.Common.Controller;
/*
Created on 2023/02/21 11:23 PM In Intelli J IDEA 
by jeon-wangi
*/

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CommonController {

    @GetMapping
    public int index() {
        return HttpStatus.OK.value();
    }
}
