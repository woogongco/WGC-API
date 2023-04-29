package com.wgc.wgcapi.Member.Entity;
/*
Created on 2023/03/09 10:27 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Member.DTO.MemberDto;
import com.wgc.wgcapi.Post.Entity.Post;
import com.wgc.wgcapi.Post.Entity.PostLike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "mail")
    private String mail;

    @Column(name = "password")
    private String password;

    @Column(name = "permission")
    private String permission = "MEMBER";

    @Column(name = "register_dt")
    @CreatedDate
    private LocalDateTime registerDateTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "writer")
    private Set<Post> posts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private Set<PostLike> postLikes = new HashSet<>();



    public Member(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public MemberDto asDto() {
        return new MemberDto(this);
    }
}
