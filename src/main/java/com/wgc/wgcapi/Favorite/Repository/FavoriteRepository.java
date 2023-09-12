package com.wgc.wgcapi.Favorite.Repository;

import com.wgc.wgcapi.Favorite.Entity.Favorite;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Post.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByMemberAndPost(Member getMember, Post getPost);
}
