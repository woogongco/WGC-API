package com.wgc.wgcapi.Homepage.Service;
/*
Created on 2023/06/27 9:24 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Homepage.DTO.GuestBookDto;
import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import com.wgc.wgcapi.Homepage.Repository.GuestBookRepository;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GuestBookWriteService {
    private final MemberService memberService;
    private final GuestBookRepository guestBookRepository;

    public ResponseDto createGuestBooks(HttpServletRequest getMember, GuestBookDto request) {
        Member writerMember = memberService.getMemberInfo(getMember);
        GuestBook guestBook = request.asGuestBookEntity(writerMember);
        guestBookRepository.save(guestBook);
        return new ResponseDto(HttpStatus.OK);

    }

    public ResponseDto modifyGuestBooks(GuestBookDto request, HttpServletRequest getMember, Long id) {
        Member writerMember = memberService.getMemberInfo(getMember);
        GuestBook guestBook = this.markGuestBookAsDeleted(id);
        guestBook.edit(request, writerMember);
        return new ResponseDto(HttpStatus.OK);

    }

    public ResponseDto deleteGuestBooks(HttpServletRequest getMember, Long id) {
        Member writerMember = memberService.getMemberInfo(getMember);
        GuestBook guestBook = this.markGuestBookAsDeleted(id);
        checkWriter(writerMember, guestBook);
        guestBook.delete();
        return new ResponseDto(HttpStatus.OK);

    }

    public GuestBook markGuestBookAsDeleted(Long id) {
        return this.guestBookRepository.findByIdAndIsDeleteEquals(id, 'N');

    }

    public void checkWriter(Member writerMember, GuestBook guestBook) {
        if (!writerMember.getId().equals(guestBook.getWriterMember().getId())) {
            new ResponseDto(HttpStatus.BAD_REQUEST);

        }
    }
}
