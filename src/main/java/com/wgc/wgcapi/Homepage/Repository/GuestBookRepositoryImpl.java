package com.wgc.wgcapi.Homepage.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import com.wgc.wgcapi.Homepage.Entity.QGuestBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class GuestBookRepositoryImpl implements CustomGuestBookRepository {

    private final JPAQueryFactory query;
    private final QGuestBook guestBook = QGuestBook.guestBook;

    @Override
    public List<GuestBook> findNonDeletedByWriterMemberId(Long writerMemberId, Long limit) {
        return query
                .selectFrom(guestBook)
                .where(
                        guestBook.writerMember.id.eq(writerMemberId),
                        guestBook.isDelete.eq('N')
                )
                .orderBy(guestBook.id.desc())
                .limit(limit)
                .fetch();
    }
}