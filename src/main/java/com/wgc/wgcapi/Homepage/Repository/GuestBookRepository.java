package com.wgc.wgcapi.Homepage.Repository;

import com.wgc.wgcapi.Homepage.Entity.GuestBook;
import com.wgc.wgcapi.Member.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long>, CustomGuestBookRepository {

    GuestBook findByIdAndIsDelete(Long id, Character isDelete);
}
