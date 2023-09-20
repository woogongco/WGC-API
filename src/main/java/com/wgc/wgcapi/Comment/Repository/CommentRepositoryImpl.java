package com.wgc.wgcapi.Comment.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wgc.wgcapi.Comment.Entity.Comment;
import com.wgc.wgcapi.Comment.Entity.QComment;
import com.wgc.wgcapi.Post.Entity.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory query;
    private final QPost post = QPost.post;
    private final QComment comment = QComment.comment;

    @Override
    public List<Comment> findCommentsByPostId(Long postId) {
        return query.select(comment)
                .from(comment)
                .join(comment.post, post)
                .where(comment.post.id.eq(postId)
                        .and(comment.parent.id.isNull())
                        .and(comment.isDelete.eq('N')))
                .fetch();
    }
}