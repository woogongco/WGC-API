package com.wgc.wgcapi.Favorite.Service;

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Favorite.DTO.ResponseFavoriteDto;
import com.wgc.wgcapi.Favorite.Entity.Favorite;
import com.wgc.wgcapi.Favorite.Repository.FavoriteRepository;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Member.Service.MemberService;
import com.wgc.wgcapi.Post.DTO.ResponsePostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteReadService {
    private final MemberService memberService;

    private final FavoriteRepository favoriteRepository;
    public ResponseDto getFavorite(HttpServletRequest request) {
        Member getMember = memberService.getMemberInfo(request);
        List<Favorite> memberFavorites = favoriteRepository.findByMember(getMember);

        List<ResponsePostDto> favoritePosts = memberFavorites.stream()
                .map(Favorite::getPost)
                .map(ResponsePostDto::new)
                .collect(Collectors.toList());
        return new ResponseDto(favoritePosts);

    }

    public ResponseDto getFavoriteByPostId(Long postId) {
        Integer countByPostId = favoriteRepository.countByPostId(postId);
        ResponseFavoriteDto responseFavoriteDto = new ResponseFavoriteDto(countByPostId);
        return new ResponseDto(responseFavoriteDto);

    }
}
