package com.wgc.wgcapi.Comment.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wgc.wgcapi.Comment.Entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    Comment findCommentByIdAndIsDeleteEquals(Long id, Character isDelete);

    List<Comment> findRepliesByParent(Comment parent);
}



