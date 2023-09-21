package com.wgc.wgcapi.Comment.Repository;

import com.wgc.wgcapi.Comment.Entity.Comment;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> findCommentsByPostId(@Param("postId") Long postId);
}
