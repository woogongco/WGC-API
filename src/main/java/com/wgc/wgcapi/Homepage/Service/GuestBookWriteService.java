package com.wgc.wgcapi.Homepage.Service;
/*
Created on 2023/06/27 9:24 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Homepage.DTO.GuestBookCreateRequest;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GuestBookWriteService {
    private final MemberService memberService;
    private final GuestBookRepository guestBookRepository;

    public ResponseDto createGuestBooks(HttpServletRequest getMember, GuestBookCreateRequest request) {
        Member writerMember = memberService.getMemberInfo(getMember);
        GuestBook guestBook = request.asGuestBookEntity(writerMember);
        guestBookRepository.save(guestBook);
        return new ResponseDto(HttpStatus.OK);

    }

    public ResponseDto searchGuestBooks(Long memberId, Long limit) {
        List<GuestBook> nonDeletedByWriterMemberId = guestBookRepository.findNonDeletedByWriterMemberId(memberId, limit);
        return new ResponseDto(nonDeletedByWriterMemberId);

    }

    public ResponseDto modifyGuestBooks(GuestBookCreateRequest request, HttpServletRequest getMember, Long id) {
        Member writerMember = memberService.getMemberInfo(getMember);
        GuestBook guestBook = this.findGuestById(id);
        guestBook.edit(request, writerMember);
        return new ResponseDto(HttpStatus.OK);

    }

    public ResponseDto deleteGuestBooks(HttpServletRequest getMember, Long id) {
        Member writerMember = memberService.getMemberInfo(getMember);
        GuestBook guestBook = this.findGuestById(id);
        checkWriter(writerMember, guestBook);
        guestBook.delete(writerMember);
        return new ResponseDto(HttpStatus.OK);

    }

    public GuestBook findGuestById(Long id) {
        return this.guestBookRepository.findByIdAndIsDelete(id, 'N');

    }

    public void checkWriter(Member writerMember, GuestBook guestBook) {
        if (!writerMember.getId().equals(guestBook.getWriterMember().getId())) {
            new ResponseDto(HttpStatus.BAD_REQUEST);

        }
    }
}
