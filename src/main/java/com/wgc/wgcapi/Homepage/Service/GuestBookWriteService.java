package com.wgc.wgcapi.Homepage.Service;
/*
Created on 2023/06/27 9:24 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Common.Exception.APIException;
import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import com.wgc.wgcapi.Homepage.Repository.GuestBookRepository;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GuestBookWriteService {


    private final GuestBookRepository guestBookRepository;

        public GuestBook createGuestBooks(GuestBook guestBook) {
            return Optional.ofNullable(guestBook)
                    .map(it ->
                            guestBookRepository.save(guestBook))
                    .orElseThrow(() -> new APIException(new ResponseDto(HttpStatus.BAD_REQUEST, "Invalid Member Information !")));
        }

    public List<GuestBook> searchGuestBooks(Member writerMember, Long limit) {
        return guestBookRepository.findNonDeletedByWriterMember(writerMember, limit);
    }


    public GuestBook modifyGuestBooks(GuestBook updateGuestBook, Long id) {
        return guestBookRepository.findById(id)
                .map(existingGuestBook -> {
                    GuestBook updatedGuestBook = GuestBook.builder()
                            .GuestBookId(existingGuestBook.getGuestBookId())
                            .content(updateGuestBook.getContent())
                            .writerMember(existingGuestBook.getWriterMember())
                            .build();
                    return guestBookRepository.save(updatedGuestBook);
                })
                .orElseThrow(() -> new APIException(new ResponseDto(HttpStatus.BAD_REQUEST, "Invalid Member Information !")));
    }

    public void deleteGuestBooks(GuestBook getGuestBook) {
        GuestBook deleteGuestBook =  getGuestBook.toBuilder()
                .isDelete("Y")
                .build();
        guestBookRepository.save(deleteGuestBook);
    }
}
