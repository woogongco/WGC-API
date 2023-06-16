package com.wgc.wgcapi.Post.Service;
/*
Created on 2023/04/12 11:54 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Post.DTO.CategoryDto;
import com.wgc.wgcapi.Post.DTO.EditPostDto;
import com.wgc.wgcapi.Post.DTO.ResponsePostDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final PostDataJpaRepository postJpaRepository;
    private final CategoryDataRepository categoryDataRepository;
    private final PostLikeWriteService postLikeWriteService;

    public ResponseDto writePost(HttpServletRequest request, WritePostDto dto) {
        Member member = this.getMemberInfo(request);
        Category category = categoryDataRepository.findCategoryById(dto.getCategoryId());
        Post post = dto.asPostEntity(member, category);
        Post save = postJpaRepository.save(post);
        log.info("new post  = {}", save);
        return new ResponseDto(HttpStatus.CREATED);
    }

    public ResponseDto editPost(HttpServletRequest request, EditPostDto dto, Long id) {
        Member member = this.getMemberInfo(request);
        Post post = this.findPostById(id);
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
        return this.postJpaRepository.findPostByIdAndIsDeleteEquals(id, 'N');
    }

    public ResponseDto deletePost(HttpServletRequest request, Long id) {
        Member member = this.getMemberInfo(request);
        Post post = this.findPostById(id);

        Long writerId = post.getWriter().getId();
        if (member.getId().equals(writerId) || member.getPermission().equals("ADMIN")) {
            post.delete();
            return new ResponseDto(HttpStatus.OK);
        }

        return new ResponseDto(HttpStatus.BAD_REQUEST);
    }

    public Member getMemberInfo(HttpServletRequest request) {
        return (Member) request.getAttribute("claim");
    }

    public ResponseDto getPost(Long id) {
        Post post = this.findPostById(id);
        if (Objects.isNull(post))
            return new ResponseDto(HttpStatus.NOT_FOUND, "post is not found !");

        ResponsePostDto dto = new ResponsePostDto(post, post.getWriter());
        return new ResponseDto(dto);
    }

    public ResponseDto getPostList(Long categoryId) {
        Category getCategory = this.findCategoryById(categoryId);

        if (Objects.isNull(getCategory))
            return new ResponseDto(HttpStatus.NOT_FOUND, "category is not found !");

        List<ResponsePostDto> postList = getCategory.getPosts().stream()
                .map(post -> new ResponsePostDto(post, post.getWriter()))
                .collect(Collectors.toList());

        return new ResponseDto(postList);
    }

    public ResponseDto getPostByCategory(Long limit) {
        List<Category> categories = categoryService.getCategory();
        Map<String, Object> result = new HashMap<>();
        categories.forEach(category -> {
            List<ResponsePostDto> post = postRepository.findPostsByCategory(category.getId(), limit);
            result.put(category.getKey(), post);
        });

        return new ResponseDto(result);
    }

    public ResponseDto likePost(HttpServletRequest request, Long id) {

        Member getMember = this.getMemberInfo(request);
        Post getPost = this.findPostById(id);

        if (Objects.isNull(getPost))
            return new ResponseDto(HttpStatus.NOT_FOUND, "post is not found !");

        return postLikeWriteService.like(getMember, getPost);


    }

    public ResponseDto dislikePost(HttpServletRequest request, Long id) {
        Member getMember = this.getMemberInfo(request);
        Post getPost = this.findPostById(id);

        if (Objects.isNull(getPost))
            return new ResponseDto(HttpStatus.NOT_FOUND, "post is not found !");

        return postLikeWriteService.dislike(getMember, getPost);

    }

    public ResponseDto getCategories() {
        return new ResponseDto(categoryService
                .getCategory()
                .stream()
                .map(CategoryDto::new)
        );
    }
}
