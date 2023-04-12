package com.wgc.wgcapi.Post.Service;
/*
Created on 2023/04/12 11:54 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Post.DTO.WritePostDto;
import com.wgc.wgcapi.Post.Entity.Category;
import com.wgc.wgcapi.Post.Entity.Post;
import com.wgc.wgcapi.Post.Repository.CategoryDataRepository;
import com.wgc.wgcapi.Post.Repository.PostDataJpaRepository;
import com.wgc.wgcapi.Post.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final PostDataJpaRepository postJpaRepository;
    private final CategoryDataRepository categoryDataRepository;

    public ResponseDto writePost(HttpServletRequest request, WritePostDto dto) {
        Member member = (Member) request.getAttribute("claim");
        Category category = categoryDataRepository.findCategoryById(dto.getCategoryId());
        Post post = dto.asPostEntity(member, category);
        Post newPost = postJpaRepository.save(post);
        return new ResponseDto(HttpStatus.CREATED, newPost);
    }

}
