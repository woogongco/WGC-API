package com.wgc.wgcapi.Homepage.Controller;
/*
Created on 2023/02/21 11:24 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.Annotations.RequireToken;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Homepage.Business.HomePageBusiness;
import com.wgc.wgcapi.Homepage.DTO.GuestBookCreateRequest;
import com.wgc.wgcapi.Homepage.Service.GuestBookReadService;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/guest-books")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookReadService homepageReadService;

    private final HomePageBusiness homePageBusiness;

//    @GetMapping("/{userId}")
//    public ResponseDto getMiniHompagePosts(
//            @PathVariable("userId")
//            Member member
//    ) {
//        return homepageReadService.getPostByUser(member);
//    }


    @PostMapping
    @RequireToken
    public ResponseDto createGuestBooks(
            @RequestBody
            GuestBookCreateRequest request, HttpServletRequest getMember
    ) {
        return homePageBusiness.createGuestBooks(getMember, request);
    }


    @GetMapping("/{id}")
    public ResponseDto searchGuestBooks(
            @PathVariable(name = "id") Long id,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Long limit
    ) {
        return homePageBusiness.searchGuestBooks(id, limit);
    }


    @PatchMapping("/{id}")
    @RequireToken
    public ResponseDto modifyGuestBooks(
            @RequestBody
            GuestBookCreateRequest request, HttpServletRequest getMember, @PathVariable(name = "id") Long id


    ) {
        return homePageBusiness.modifyGuestBooks(request,getMember, id);
    }

    @DeleteMapping("/{id}")
    @RequireToken
    public ResponseDto deleteGuestBooks(
            @PathVariable(name = "id")
            Long id,HttpServletRequest getMember


    ) {
        return homePageBusiness.deleteGuestBooks(getMember, id);
    }
}
