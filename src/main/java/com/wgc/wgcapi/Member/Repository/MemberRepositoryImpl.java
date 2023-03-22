package com.wgc.wgcapi.Member.Repository;
/*
Created on 2023/03/09 10:46 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Member.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class MemberRepositoryImpl {

    @PersistenceContext
    private final EntityManager em;

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    public void signUp(Member member) {
        em.persist(member);
    }
}
