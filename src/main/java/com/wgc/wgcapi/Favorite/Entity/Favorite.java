package com.wgc.wgcapi.Favorite.Entity;

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Post.Entity.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "favorite_at")
    @CreatedDate
    private LocalDateTime favoriteAt;
    @Embedded
    private Favorites favorites = Favorites.empty();

    public Favorite(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

    public void associateWithPost(Post getPost) {
        this.post = getPost;
    }

    public void associateWithMember(Member getMember) {
        this.member = getMember;
        getFavorites().addFavorite(this);
    }
}