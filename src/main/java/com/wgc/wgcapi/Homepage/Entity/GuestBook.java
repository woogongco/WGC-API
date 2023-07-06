package com.wgc.wgcapi.Homepage.Entity;

import com.wgc.wgcapi.Member.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@Builder(toBuilder = true)
@Getter
public class GuestBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long GuestBookId;

    @Column(name = "content")
    public String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    public Member writerMember;

    @Column(name = "is_delete")
    public String isDelete;

    @PrePersist
    public void prePersist() {
        this.isDelete = this.isDelete == null ? "N" : this.isDelete;
    }
}