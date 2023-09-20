package com.wgc.wgcapi.Favorite.Service;

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Favorite.Entity.Favorite;
import com.wgc.wgcapi.Favorite.Repository.FavoriteRepository;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Member.Service.MemberService;
import com.wgc.wgcapi.Post.DTO.ResponsePostDto;
import com.wgc.wgcapi.Post.Entity.Post;
import com.wgc.wgcapi.Post.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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


    public ResponseDto deleteFavorite(Long id, HttpServletRequest request) {
        Member getMember = memberService.getMemberInfo(request);
        Post getPost = postService.findPostById(id);
        Optional<Favorite> existingFavorite = favoriteRepository.findByMemberAndPost(getMember, getPost);

        if(existingFavorite.isEmpty()){
            return new ResponseDto(HttpStatus.BAD_REQUEST, "Not bookmarked");
        }

            favoriteRepository.delete(existingFavorite.get());
            return new ResponseDto(HttpStatus.OK);

    }


    public ResponseDto getFavorite(HttpServletRequest request) {
        Member getMember = memberService.getMemberInfo(request);
        List<Favorite> memberFavorites = favoriteRepository.findByMember(getMember);

        List<ResponsePostDto> favoritePosts = memberFavorites.stream()
                .map(Favorite::getPost)
                .map(ResponsePostDto::new)
                .collect(Collectors.toList());

        return new ResponseDto(favoritePosts);


    }
}
