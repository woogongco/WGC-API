package com.wgc.wgcapi.Post.Entity;
/*
Created on 2023/04/12 11:57 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Post.DTO.EditPostDto;
import com.wgc.wgcapi.Post.DTO.WritePostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post", catalog = "wgc")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private Set<PostLike> postLikes = new HashSet<>();

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "`like`")
    private Long like = 0L;

    @Column(name = "view")
    private Long view = 0L;

    @Column(name = "register_date")
    @CreatedDate
    private LocalDateTime registerDate;

    @Column(name = "last_update")
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;

    @Column(name = "is_delete")
    private Character isDelete;

    public Post(Member member, Category category, WritePostDto writePostDto) {
        this.writer = member;
        this.category = category;
        this.title = writePostDto.getTitle();
        this.content = writePostDto.getContent();
    }

    public void edit(EditPostDto dto, Category category) {
        this.category = category;
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void delete() {
        this.isDelete = 'Y';
    }

    public void incrementLikeCount() { this.like++;}


    public void decrementLikeCount() { this.like--;}


}
