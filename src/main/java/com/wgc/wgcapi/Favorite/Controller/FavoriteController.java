package com.wgc.wgcapi.Favorite.Controller;

import com.wgc.wgcapi.Common.Annotations.RequireToken;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Favorite.Service.FavoriteReadService;
import com.wgc.wgcapi.Favorite.Service.FavoriteWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteWriteService favoriteWriteService;
    private final FavoriteReadService favoriteReadService;

    @PostMapping("/{id}")
    @RequireToken
    public ResponseDto addFavorite(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        return this.favoriteWriteService.addFavorite(id, request);
    }

    @DeleteMapping("/{id}")
    @RequireToken
    public ResponseDto deleteFavorite(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        return this.favoriteWriteService.deleteFavorite(id, request);
    }

    @GetMapping
    @RequireToken
    public ResponseDto getFavorite(HttpServletRequest request) {
        return this.favoriteReadService.getFavorite(request);
    }

    @GetMapping("/posts/{postId}")
    public ResponseDto getFavoriteByPostId(@PathVariable(name = "postId") Long postId) {
        return this.favoriteReadService.getFavoriteByPostId(postId);
    }
}
