package com.wgc.wgcapi.Favorite.Service;

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Favorite.DTO.FavoriteRequest;
import com.wgc.wgcapi.Favorite.Entity.Favorite;
import com.wgc.wgcapi.Favorite.Repository.FavoriteRepository;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Member.Service.MemberService;
import com.wgc.wgcapi.Post.Entity.Post;
import com.wgc.wgcapi.Post.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final MemberService memberService;

    private final PostService postService;
    private final FavoriteRepository favoriteRepository;
    public ResponseDto addFavorite(Long id, HttpServletRequest request) {

        Member getMember = memberService.getMemberInfo(request);
        Post getPost = postService.findPostById(id);
        Favorite favorite = new Favorite();

        if(favoriteRepository.existsByMemberAndPost(getMember,getPost)){
            return new ResponseDto(HttpStatus.BAD_REQUEST, "Already bookmarked");
        }
        favorite.setPost(getPost);
        favorite.setMmeber(getMember);
        favoriteRepository.save(favorite);

        return new ResponseDto(HttpStatus.CREATED);
    }



}
