package com.wgc.wgcapi.Homepage.Service;
/*
Created on 2023/06/27 9:24 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Homepage.DTO.GuestBookResponse;
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
    private final GuestBookRepository guestBookRepository;

    public ResponseDto getPostByUser(Member member, Long limit) {
        List<GuestBookResponse>guestbooks = guestBookRepository.findNonDeletedByWriterMemberId(member.getId(), limit)
                .stream()
                .map(GuestBookResponse::new)
                .collect(Collectors.toList());

        return new ResponseDto(guestbooks);
    }
}
