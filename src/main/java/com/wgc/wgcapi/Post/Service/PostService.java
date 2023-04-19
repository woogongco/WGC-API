package com.wgc.wgcapi.Post.Service;
/*
Created on 2023/04/12 11:54 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Post.DTO.EditPostDto;
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
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostDataJpaRepository postJpaRepository;
    private final CategoryDataRepository categoryDataRepository;

    public ResponseDto writePost(HttpServletRequest request, WritePostDto dto) {
        Member member = this.getMemberInfo(request);
        Category category = categoryDataRepository.findCategoryById(dto.getCategoryId());
        Post post = dto.asPostEntity(member, category);
        Post save = postJpaRepository.save(post);
        log.info("new post  = {}", save);
        return new ResponseDto(HttpStatus.CREATED);
    }

    public ResponseDto editPost(HttpServletRequest request, EditPostDto dto) {
        Member member = this.getMemberInfo(request);
        Post post = this.findPostById(dto.getId());
        Long writerMemberId = post.getWriter().getId();
        if (member.getPermission().equals("ADMIN") || member.getId().equals(writerMemberId)) {
            Category category = this.findCategoryById(dto.getCategoryId());
            post.edit(dto, category);
            return new ResponseDto(HttpStatus.OK);
        }

        return new ResponseDto(HttpStatus.BAD_REQUEST);
    }

    public Category findCategoryById(Long id) {
        return this.categoryDataRepository.findById(id).get();
    }

    public Post findPostById(Long id) {
        return this.postJpaRepository.findById(id).get();
    }

    public ResponseDto deletePost(HttpServletRequest request, Long id) {
        Member member = this.getMemberInfo(request);
        Post post = this.findPostById(id);

        Long writerId = post.getWriter().getId();
        if (member.getId().equals(writerId) || member.getPermission().equals("ADMIN"))
            post.delete();

        return new ResponseDto(HttpStatus.BAD_REQUEST);
    }

    private Member getMemberInfo(HttpServletRequest request) {
        return (Member) request.getAttribute("claim");
    }
}
