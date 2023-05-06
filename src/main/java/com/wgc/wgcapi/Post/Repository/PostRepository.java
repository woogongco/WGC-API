package com.wgc.wgcapi.Post.Repository;
/*
Created on 2023/04/13 12:10 AM In Intelli J IDEA 
by jeon-wangi
*/

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wgc.wgcapi.Member.DTO.MemberDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Member.Entity.QMember;
import com.wgc.wgcapi.Post.DTO.ResponsePostDto;
import com.wgc.wgcapi.Post.Entity.Post;
import com.wgc.wgcapi.Post.Entity.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostRepository {

    private final JPAQueryFactory query;
    private final QPost post = QPost.post;
    private final QMember member = QMember.member;

    public List<ResponsePostDto> findPostsByCategory(Long categoryId,Long limit) {
        return query
                .select(Projections.bean(
                                ResponsePostDto.class,
                                post.id,
                                post.title,
                                post.content,
                                post.like,
                                post.view,
                                post.registerDate,
                                post.lastUpdateDate.as("lastModifiedDate")
                        )
                )
                .from(post)
                .where(
                        post.category.id.eq(categoryId)
                        , post.isDelete.eq('N')
                )
                .orderBy(post.id.desc())
                .limit(limit)
                .fetch();
    }


}
