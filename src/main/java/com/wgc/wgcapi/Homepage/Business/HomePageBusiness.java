package com.wgc.wgcapi.Homepage.Business;

import com.wgc.wgcapi.Common.Annotations.Business;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Common.Exception.APIException;
import com.wgc.wgcapi.Homepage.DTO.GuestBookCreateRequest;
import com.wgc.wgcapi.Homepage.Converter.GuestBookConverter;
import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import com.wgc.wgcapi.Homepage.Service.GuestBookReadService;
import com.wgc.wgcapi.Homepage.Service.GuestBookWriteService;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequiredArgsConstructor
@Business
public class HomePageBusiness {

    private final GuestBookWriteService homepageWriteService;

    private final GuestBookReadService homepageReadService;

    private final GuestBookConverter homepageConverter;

    private final MemberService memberService;
    /**
     * 방명록 생성
     */
    public ResponseDto createGuestBooks(HttpServletRequest getMember, GuestBookCreateRequest request) {
        Member writerMember = memberService.getMemberInfo(getMember);
       var entity = homepageConverter.toEntity(request,writerMember);
       var newEntity = homepageWriteService.createGuestBooks(entity);
        return homepageConverter.toResponse(newEntity);
    }

    /**
     * 방명록 조회
     */

    public ResponseDto searchGuestBooks(Long id,Long limit ) {
        List<GuestBook> guestBookList = homepageWriteService.searchGuestBooks(id,limit);
        return homepageConverter.toListResponse(guestBookList);
    }

    /**
     * 방명록 수정
     */

    public ResponseDto modifyGuestBooks(GuestBookCreateRequest request, HttpServletRequest getMember, Long id) {
        Member writerMember = memberService.getMemberInfo(getMember);
        var entity = homepageConverter.toEntity(request,writerMember);
        var newEntity = homepageWriteService.modifyGuestBooks(entity,id);
        return homepageConverter.toResponse(newEntity);
    }

    public ResponseDto deleteGuestBooks(HttpServletRequest getMember, Long id) {

        Member writerMember = memberService.getMemberInfo(getMember);
        GuestBook guestBook = homepageReadService.getGuestbooks(id);
        checkWriter(writerMember, guestBook);
        homepageWriteService.deleteGuestBooks(guestBook);

        return new ResponseDto(HttpStatus.OK);
    }


    private static void checkWriter(Member writerMember, GuestBook guestBook) {
        if(!writerMember.getId().equals(guestBook.getWriterMember().getId())) {
            throw new APIException(new ResponseDto(HttpStatus.BAD_REQUEST,
                "WriterMember ID does not match with GuestBook's writerMember ID."));
        }
}
    }