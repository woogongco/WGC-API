package com.wgc.wgcapi.Comment.Repository;

import com.wgc.wgcapi.Comment.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentByIdAndIsDeleteEquals(Long id, Character isDelete);

    @Query(value = "SELECT c FROM Comment c WHERE c.post.id = :postId and c.parent.id is null")
    List<Comment> findCommentsByPostId(@Param("postId") Long postId);

    List<Comment> findRepliesByParent(Comment parent);
}
