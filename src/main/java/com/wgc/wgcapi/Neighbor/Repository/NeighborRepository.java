package com.wgc.wgcapi.Neighbor.Repository;
/*
Created on 2023/06/26 10:34 PM In Intelli J IDEA
by jeon-wangi
*/

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Neighbor.DTO.NeighborDto;
import com.wgc.wgcapi.Neighbor.Entity.Neighbor;
import com.wgc.wgcapi.Neighbor.Entity.QNeighbor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NeighborRepository {

    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory query;
    private final QNeighbor neighbor = QNeighbor.neighbor;


    public List<NeighborDto> getNeighborList(Long id) {
        try {
            return query
                    .select(
                            Projections.bean(
                                    NeighborDto.class,
                                    neighbor.acceptMember.id.as("memberId"),
                                    neighbor.acceptMember.name,
                                    neighbor.acceptMember.mail,
                                    neighbor.isAccept.as("accepted"),
                                    neighbor.requestDateTime
                            )
                    )
                    .from(neighbor)
                    .where(
                            neighbor.isDelete.eq('N')
                            , neighbor.requestMember.id.eq(id)
                    )
                    .orderBy(
                            neighbor.requestDateTime.desc()
                    )
                    .fetch();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public Neighbor getNeighborStatus(Long requestUserId, Long targetUserId) {
        return query
                .selectFrom(neighbor)
                .where(
                        neighbor.requestMember.id.eq(requestUserId)
                                .or(neighbor.requestMember.id.eq(targetUserId))
                                .or(neighbor.acceptMember.id.eq(requestUserId))
                                .or(neighbor.acceptMember.id.eq(targetUserId))
                                .and(neighbor.isDelete.eq('N'))
                )
                .fetchFirst();
    }
}
