package com.wgc.wgcapi.Homepage.Controller;
/*
Created on 2023/02/21 11:24 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.Annotations.RequireToken;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Homepage.DTO.GuestBookDto;
import com.wgc.wgcapi.Homepage.Service.GuestBookReadService;
import com.wgc.wgcapi.Homepage.Service.GuestBookWriteService;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/guestbook")
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookReadService homepageReadService;
    private final GuestBookWriteService homepageWriteService;

    @PostMapping
    @RequireToken
    public ResponseDto createGuestBooks(@RequestBody GuestBookDto request,
                                        HttpServletRequest getMember) {
        return this.homepageWriteService.createGuestBooks(getMember, request);
    }

    @GetMapping("/{memberId}")
    public ResponseDto getGuestBooks(@PathVariable("memberId") Member member,
                                           @RequestParam(value = "limit", required = false, defaultValue = "10")
                                           Long limit) {
        return this.homepageReadService.getGuestBooksByUser(member,limit);
    }

    @PutMapping("/{id}")
    @RequireToken
    public ResponseDto modifyGuestBooks(@RequestBody GuestBookDto request,
                                        HttpServletRequest getMember,
                                        @PathVariable(name = "id") Long id) {
        return this.homepageWriteService.modifyGuestBooks(request, getMember, id);
    }

    @DeleteMapping("/{id}")
    @RequireToken
    public ResponseDto deleteGuestBooks(@PathVariable(name = "id") Long id,
                                        HttpServletRequest getMember) {
        return this.homepageWriteService.deleteGuestBooks(getMember, id);
    }
}
