package com.wgc.wgcapi.Homepage.Service;
/*
Created on 2023/06/27 9:24 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Homepage.DTO.ResponseGuestBookDto;
import com.wgc.wgcapi.Homepage.Repository.GuestBookRepository;
import com.wgc.wgcapi.Member.Entity.Member;
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

    public ResponseDto getGuestBooksByUser(Member member, Long limit) {
        List<ResponseGuestBookDto> guestbooks = guestBookRepository.findNonDeletedByWriterMemberId(member.getId(), limit)
                .stream()
                .map(guestBook -> new ResponseGuestBookDto(guestBook, guestBook.getWriterMember()))
                .collect(Collectors.toList());
        return new ResponseDto(guestbooks);
    }
}
