package com.wgc.wgcapi.Post.Service;
/*
Created on 2023/04/12 11:54 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Authentication.Service.JwtService;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Member.Repository.MemberRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.*;

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
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

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
        if (isNull(post))
            return new ResponseDto(HttpStatus.NOT_FOUND, "post is not found !");

        ResponsePostDto dto = new ResponsePostDto(post, post.getWriter());
        return new ResponseDto(dto);
    }

    public ResponseDto getPostList(Long categoryId, Long limit) {
        Category category = this.findCategoryById(categoryId);

        if (isNull(category))
            return new ResponseDto(HttpStatus.NOT_FOUND, "category is not found !");

        List<Post> posts = postJpaRepository.findTop10ByIsDeleteIsAndCategoryIdOrderByRegisterDateDesc('N', category.getId());

        List<ResponsePostDto> result = posts.stream().map(post -> new ResponsePostDto(post, post.getWriter())).collect(Collectors.toList());
        return new ResponseDto(result);
    }

    public ResponseDto getPostByCategory(Long limit) {
        List<Category> categories = categoryService.getCategory();
        Map<String, Object> result = new HashMap<>();
        categories.forEach(category -> {
            List<ResponsePostDto> post = postRepository.findPostsByCategory(category.getId(), limit);
            result.put(category.getKey(), post);
        });

        result.put("popular", this.popularPostsAsDto());

        return new ResponseDto(result);
    }

    private List<ResponsePostDto> popularPostsAsDto() {
        return getPopularPosts()
                .stream()
                .map(ResponsePostDto::new)
                .collect(Collectors.toList());
    }

    private List<Post> getPopularPosts() {
        return postJpaRepository.findAllByIsDeleteIsOrderByLikeDesc('N', PageRequest.of(0, 10));
    }

    public ResponseDto likePost(HttpServletRequest request, Long id) {

        Member getMember = this.getMemberInfo(request);
        Post getPost = this.findPostById(id);

        if (isNull(getPost))
            return new ResponseDto(HttpStatus.NOT_FOUND, "post is not found !");

        return postLikeWriteService.like(getMember, getPost);
    }

    public ResponseDto dislikePost(HttpServletRequest request, Long id) {
        Member getMember = this.getMemberInfo(request);
        Post getPost = this.findPostById(id);

        if (isNull(getPost))
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

    public List<Post> getPostByUserId(Member member) {
        return postJpaRepository.findPostsByWriterIdAndIsDeleteIsOrderByRegisterDateDesc(member.getId(), 'N');
    }

    public ResponseDto getPostsByUserId(HttpServletRequest request, Member member) throws IllegalAccessException {
        if (nonNull(member)) {
            List<ResponsePostDto> result = getPostByUserId(member)
                    .stream()
                    .map(i -> new ResponsePostDto(i, i.getWriter()))
                    .collect(Collectors.toList());
            return new ResponseDto(HttpStatus.OK, result);
        }

        Member requestUser = jwtService.validate(request.getHeader("Authorization"));
        if (isNull(requestUser))
            throw new IllegalAccessException("토큰이 없거나 user id가 없습니다.");

        return new ResponseDto(HttpStatus.OK, getPostByUserId(requestUser).stream().map(i -> new ResponsePostDto(i, i.getWriter())));
    }
}
