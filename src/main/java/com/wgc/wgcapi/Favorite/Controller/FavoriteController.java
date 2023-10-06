package com.wgc.wgcapi.Favorite.Controller;

import com.wgc.wgcapi.Common.Annotations.RequireToken;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Favorite.Service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{id}")
    @RequireToken
    public ResponseDto addFavorite(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        return this.favoriteService.addFavorite(id, request);
    }

    @DeleteMapping("/{id}")
    @RequireToken
    public ResponseDto deleteFavorite(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        return this.favoriteService.deleteFavorite(id, request);
    }

    @GetMapping
    @RequireToken
    public ResponseDto getFavorite(HttpServletRequest request) {
        return this.favoriteService.getFavorite(request);
    }

    @GetMapping("/posts/{postId}")
    public ResponseDto getFavoriteByPostId(@PathVariable(name = "postId") Long postId) {
        return this.favoriteService.getFavoriteByPostId(postId);
    }
}
