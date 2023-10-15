package com.wgc.wgcapi.Member.Repository;
/*
Created on 2023/03/22 11:44 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Member.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, CrudRepository<Member, Long> {

    Member findByMail(String mail);

    Member findMemberByMailIsAndPasswordIs(String name, String password);

    Member findMemberByIdIs(Long id);
}
