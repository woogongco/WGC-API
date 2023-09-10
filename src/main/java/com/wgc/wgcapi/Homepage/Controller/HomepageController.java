package com.wgc.wgcapi.Homepage.Controller;
/*
Created on 2023/02/21 11:24 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Homepage.Service.HomepageService;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/homepage")
@RequiredArgsConstructor
public class HomepageController {

    private final HomepageService homepageService;

    @GetMapping("/post/{userId}")
    public ResponseDto getMiniHompagePosts(@PathVariable("userId") Member member) {
        return homepageService.getPostByUser(member);
    }

}
