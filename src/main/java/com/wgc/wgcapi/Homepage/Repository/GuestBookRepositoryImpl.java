package com.wgc.wgcapi.Homepage.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import com.wgc.wgcapi.Homepage.Entity.QGuestBook;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Member.Entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class GuestBookRepositoryImpl implements CustomGuestBookRepository {

    private final JPAQueryFactory query;
    private final QGuestBook guestBook = QGuestBook.guestBook;

    public List<GuestBook> findNonDeletedByWriterMember(Member writerMember, Long limit) {
        return query
                .selectFrom(guestBook)
                .where(
                        guestBook.writerMember.eq(writerMember),
                        guestBook.isDelete.eq("N")
                )
                .orderBy(guestBook.GuestBookId.desc())
                .limit(limit)
                .fetch();

    }
}