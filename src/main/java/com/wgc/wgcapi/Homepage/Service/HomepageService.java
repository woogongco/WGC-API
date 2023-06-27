package com.wgc.wgcapi.Homepage.Service;
/*
Created on 2023/06/27 9:24 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Post.DTO.ResponsePostDto;
import com.wgc.wgcapi.Post.Service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HomepageService {

    private final PostService postService;

    public ResponseDto getPostByUser(Member member) {
        List<ResponsePostDto> posts = postService.getPostByUserId(member)
                .stream()
                .map(ResponsePostDto::new)
                .collect(Collectors.toList());

        return new ResponseDto(posts);
    }
}
