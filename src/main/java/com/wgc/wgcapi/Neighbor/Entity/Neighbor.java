package com.wgc.wgcapi.Neighbor.Entity;
/*
Created on 2023/06/26 10:15 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Neighbor.Enums.NeighborStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@DynamicUpdate
@AllArgsConstructor
@Table(name = "neighbor", catalog = "wgc")
public class Neighbor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "request_member_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member requestMember;

    @JoinColumn(name = "accept_member_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member acceptMember;

    @Column(name = "request_dt")
    @CreatedDate
    private LocalDateTime requestDateTime;

    @Column(name = "is_accept")
    private Character isAccept = 'R';

    @Column(name = "is_delete")
    private Character isDelete = 'N';

    public Neighbor(Member requestMember, Member acceptMember) {
        this.requestMember = requestMember;
        this.acceptMember = acceptMember;
    }

    public void updateRequestStatus(NeighborStatus status) {
        this.isAccept = status.vale();
    }
}
