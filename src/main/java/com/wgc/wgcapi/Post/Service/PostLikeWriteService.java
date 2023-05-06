package com.wgc.wgcapi.Post.Service;

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Post.Entity.Post;
import com.wgc.wgcapi.Post.Entity.PostLike;
import com.wgc.wgcapi.Post.Repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeWriteService {
    private final PostLikeRepository postLikeRepository;

    public ResponseDto like(Member member, Post post) {
        if (checkLiked(member, post)) {
            return new ResponseDto(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 게시글입니다.");
        }

        PostLike newPostLike = PostLike.builder().member(member).post(post).build();
        post.incrementLikeCount();
        postLikeRepository.save(newPostLike);
        return new ResponseDto(HttpStatus.OK, "좋아요를 눌렀습니다.");
    }

    public ResponseDto dislike(Member member, Post post) {
        Optional<PostLike> getPostLike = postLikeRepository.findByMemberAndPost(member, post);

        if (getPostLike.isEmpty()) {
            return new ResponseDto(HttpStatus.BAD_REQUEST, "좋아요를 누르지 않은 게시글입니다.");
        }

        post.decrementLikeCount();
        postLikeRepository.delete(getPostLike.get());
        return new ResponseDto(HttpStatus.OK, "좋아요가 취소되었습니다.");
    }

    private boolean checkLiked(Member member, Post post) {
        return postLikeRepository.existsByMemberAndPost(member, post);
    }
}

