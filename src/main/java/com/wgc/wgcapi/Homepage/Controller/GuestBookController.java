package com.wgc.wgcapi.Homepage.Controller;
/*
Created on 2023/02/21 11:24 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.Annotations.RequireToken;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Homepage.Business.HomePageBusiness;
import com.wgc.wgcapi.Homepage.Controller.request.GuestBookCreateRequest;
import com.wgc.wgcapi.Homepage.Service.GuestBookReadService;
import com.wgc.wgcapi.Member.Entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/homepage")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookReadService homepageReadService;

    private final HomePageBusiness homePageBusiness;

    @GetMapping("/post/{userId}")
    @Operation(summary = "유저 게시글 조회",description = "유저 게시글 조회")
    public ResponseDto getMiniHompagePosts(
            @PathVariable("userId")
            Member member
    ) {
        return homepageReadService.getPostByUser(member);
    }


    @PostMapping("/guest-books")
    @RequireToken
    @Operation(summary = "방명록 작성",description = "방명록 작성")
    public ResponseDto createGuestBooks(
            @Valid
            @RequestBody
            GuestBookCreateRequest request, HttpServletRequest getMember
    ) {
        return homePageBusiness.createGuestBooks(getMember, request);
    }


    @GetMapping("/guest-books/list")
    @RequireToken
    @Operation(summary = "방명록 조회",description = "방명록 조회")
    public ResponseDto searchGuestBooks(
            HttpServletRequest getMember,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Long limit
    ) {
        return homePageBusiness.searchGuestBooks(getMember, limit);
    }


    @PatchMapping("/guest-books/{id}")
    @RequireToken
    @Operation(summary = "방명록 수정",description = "방명록 수정")
    public ResponseDto modifyGuestBooks(
            @Valid
            @RequestBody
            GuestBookCreateRequest request, HttpServletRequest getMember, @PathVariable(name = "id") Long id


    ) {
        return homePageBusiness.modifyGuestBooks(request,getMember, id);
    }

    @DeleteMapping("/guest-books/{id}")
    @RequireToken
    @Operation(summary = "방명록 삭제",description = "방명록 삭제")
    public ResponseDto deleteGuestBooks(
            @PathVariable(name = "id")
            Long id,HttpServletRequest getMember


    ) {
        return homePageBusiness.deleteGuestBooks(getMember, id);
    }
}
