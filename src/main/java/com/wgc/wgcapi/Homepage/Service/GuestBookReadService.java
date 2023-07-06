package com.wgc.wgcapi.Homepage.Service;
/*
Created on 2023/06/27 9:24 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import com.wgc.wgcapi.Homepage.Repository.GuestBookRepository;
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
public class GuestBookReadService {

    private final PostService postService;
    private final GuestBookRepository guestBookRepository;


    //todo 추후 수정 예정

    public ResponseDto getPostByUser(Member member) {
        List<ResponsePostDto> posts = postService.getPostByUserId(member)
                .stream()
                .map(ResponsePostDto::new)
                .collect(Collectors.toList());

        return new ResponseDto(posts);
    }

    public GuestBook getGuestbooks(Long id) {
        return guestBookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
    }
}
