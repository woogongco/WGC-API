package com.wgc.wgcapi.Member.Controller;
/*
Created on 2023/06/15 8:42 PM In Intelli J IDEA 
by jeon-wangi
*/

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/social")
public class SocialLoginController {

    @GetMapping
    public ModelAndView socialLoginIndex() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index.html");
        return mv;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> kakaoLoginTest(@RequestBody Map<String, Object> body) {
        System.out.println(body);
        return ResponseEntity.noContent().build();
    }
}
