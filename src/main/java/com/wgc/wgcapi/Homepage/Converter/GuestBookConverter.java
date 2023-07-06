package com.wgc.wgcapi.Homepage.Converter;

import com.wgc.wgcapi.Common.Annotations.Converter;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Common.Exception.APIException;
import com.wgc.wgcapi.Homepage.Controller.request.GuestBookCreateRequest;
import com.wgc.wgcapi.Homepage.DTO.GuestBookResponse;
import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Post.DTO.ResponsePostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Converter
public class GuestBookConverter {

    public GuestBook toEntity(GuestBookCreateRequest request, Member writeMember) {
        return Optional.ofNullable(request)
                .map(it -> GuestBook.builder()
                        .writerMember(writeMember)
                        .content(request.getContent())
                        .build())
                .orElseThrow(() -> new APIException(new ResponseDto(HttpStatus.BAD_REQUEST, "Invalid Member Information !")));
    }




    public GuestBookResponse toDto(GuestBook guestBook) {
        return new GuestBookResponse(
                guestBook.getGuestBookId(),
                guestBook.getWriterMember().getName(),
                guestBook.getContent()
        );
    }

    public ResponseDto toResponse(GuestBook guestBook) {
        return new ResponseDto(toDto(guestBook));
    }

    public ResponseDto toListResponse(List<GuestBook> guestBooks) {
        return new ResponseDto(
                guestBooks.stream()
                        .map(this::toDto)
                        .collect(Collectors.toList())
        );
    }

//    public ResponseDto toResponse(GuestBook guestBook) {
//
//        return Optional.ofNullable(guestBook)
//                .map(it -> {
//                             GuestBook.builder()
//                            .GuestBookId(guestBook.getGuestBookId())
//                            .content(guestBook.getContent())
//                            .writerMember(guestBook.getWriterMember())
//                            .build();
//
//                    GuestBookResponse dto = new GuestBookResponse(
//                            guestBook.getGuestBookId(),guestBook.getContent(),guestBook.getWriterMember().getName());
//                           return new ResponseDto(dto);
//                })
//                .orElseThrow(() -> new APIException(new ResponseDto(HttpStatus.BAD_REQUEST, "Invalid Member Information !")));
//    }
//
//    public ResponseDto toListResponse(GuestBook guestBook) {
//        return Optional.ofNullable(guestBook)
//                .map(it -> {
//                    GuestBookResponse dto = new GuestBookResponse(it.getGuestBookId(),it.getContent(),it.getWriterMember().getName());
//                    return new ResponseDto(dto);
//                })
//                .orElseThrow(() -> new APIException(new ResponseDto(HttpStatus.BAD_REQUEST, "Invalid Member Information !")));
//    }
}
