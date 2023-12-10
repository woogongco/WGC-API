package com.wgc.wgcapi.Homepage.Entity;

import com.wgc.wgcapi.Homepage.DTO.GuestBookDto;
import com.wgc.wgcapi.Member.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@Builder(toBuilder = true)
@Getter
@Entity
public class GuestBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "content")
    public String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    public Member writerMember;

    @Column(name = "is_delete")
    private Character isDelete = 'N';

    @Column(name = "register_date")
    @CreatedDate
    private LocalDateTime registerDate;

    @Column(name = "last_update")
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;

    public GuestBook(Member writerMember, GuestBookDto guestBookCreateRequest) {
        this.writerMember = writerMember;
        this.content = guestBookCreateRequest.getContent();
    }

    public void edit(GuestBookDto request, Member writerMember) {
        this.content = request.getContent();
        this.writerMember = writerMember;
    }

    public void delete() {
        this.isDelete = 'Y';
    }
}