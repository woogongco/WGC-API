package com.wgc.wgcapi.Homepage.Repository;

import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import com.wgc.wgcapi.Member.Entity.Member;

import java.util.List;

public interface CustomGuestBookRepository {

    List<GuestBook> findNonDeletedByWriterMember(Member writerMember, Long limit);
}
