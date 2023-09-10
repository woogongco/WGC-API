package com.wgc.wgcapi.Homepage.Converter;

import com.wgc.wgcapi.Common.Annotations.Converter;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Common.Exception.APIException;
import com.wgc.wgcapi.Homepage.DTO.GuestBookCreateRequest;
import com.wgc.wgcapi.Homepage.DTO.GuestBookResponse;
import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

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
                guestBook.getId(),
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


}
