package com.wgc.wgcapi.Neighbor.Repository;
/*
Created on 2023/06/26 10:32 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Neighbor.Entity.Neighbor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface NeighborDataRepository extends JpaRepository<Neighbor, Long>, CrudRepository<Neighbor, Long> {

    @Override
    <S extends Neighbor> S save(S entity);

    Neighbor findByRequestMemberIdAndAcceptMemberIdAndIsDelete(Long requestMemberId, Long acceptMemberId, Character isDelete);

}
