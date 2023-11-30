package com.wgc.wgcapi.Comment.Service;

import com.wgc.wgcapi.Comment.DTO.*;
import com.wgc.wgcapi.Comment.Entity.Comment;
import com.wgc.wgcapi.Comment.Repository.CommentRepository;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Member.Service.MemberService;
import com.wgc.wgcapi.Notification.Entity.Notification;
import com.wgc.wgcapi.Notification.Entity.NotificationType;
import com.wgc.wgcapi.Notification.service.NotificationStorageService;
import com.wgc.wgcapi.Post.Entity.Post;
import com.wgc.wgcapi.Post.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentWriteService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final PostService postService;
    private final NotificationStorageService notificationService;
    public static final String COMMENT_ADDED_CONTENT = "A comment has been added: %s";

    public ResponseDto addComment(RequestComment dto, HttpServletRequest request, Long postId) {

        Member getMember = memberService.getMemberInfo(request);
        Post getPost = postService.findPostById(postId);
        String notificationContent = String.format(COMMENT_ADDED_CONTENT, dto.getContent());
        Comment comment = Comment.parent(getMember, getPost, dto.getContent());
        Notification notification = new Notification(
                notificationContent,
                NotificationType.COMMENT,
                getMember,
                getPost.getWriter()
        );
        notificationService.createNotificationStorage(notification);
        commentRepository.save(comment);

        return new ResponseDto(HttpStatus.CREATED);
    }

    public ResponseDto addReply(RequestReply dto, HttpServletRequest request, Long commentId) {

        Comment parent = this.findPostById(commentId);
        Member getMember = memberService.getMemberInfo(request);
        if (!parent.isParent()) {
            return new ResponseDto(HttpStatus.NOT_FOUND, "parent is not found !");
        }
        Post getPost = parent.getPost();
        Comment reply = Comment.child(getMember, getPost, dto.getContent(), parent);
        String notificationContent = String.format(COMMENT_ADDED_CONTENT, dto.getContent());
        Notification notification = new Notification(
                notificationContent,
                NotificationType.COMMENT,
                getMember,
                getPost.getWriter()
        );
        notificationService.createNotificationStorage(notification);
        commentRepository.save(reply);

        return new ResponseDto(HttpStatus.CREATED);
    }


    public ResponseDto editComment(RequestComment dto, HttpServletRequest request, Long commentId) {

        Member getMember = memberService.getMemberInfo(request);
        Comment getComment = this.findPostById(commentId);
        Long writerId = getComment.getWriter().getId();
        if (getMember.getPermission().equals("ADMIN") || getMember.getId().equals(writerId)) {
            getComment.edit(dto);
            return new ResponseDto(HttpStatus.OK);
        }
        return new ResponseDto(HttpStatus.BAD_REQUEST);
    }

    public ResponseDto deleteComment(HttpServletRequest request, Long commentId) {

        Member getMember = memberService.getMemberInfo(request);
        Comment getComment = this.findPostById(commentId);
        Long writerId = getComment.getWriter().getId();
        if (getMember.getPermission().equals("ADMIN") || getMember.getId().equals(writerId)) {
            getComment.changePretendingToBeRemoved();
            deleteCommentOrReply(getComment);
            return new ResponseDto(HttpStatus.OK);
        }
        return new ResponseDto(HttpStatus.BAD_REQUEST);
    }

    public Comment findPostById(Long id) {
        return this.commentRepository.findCommentByIdAndIsDeleteEquals(id, 'N');
    }

    private void deleteCommentOrReply(Comment comment) {
        if (comment.isParent()) {
            deleteParent(comment);
            return;
        }
        deleteChild(comment);
    }

    private void deleteParent(Comment comment) {
        if (comment.hasNoReply()) {
            commentRepository.delete(comment);
            return;
        }
        commentRepository.deleteAll(comment.getChildren());
        comment.changePretendingToBeRemoved();
    }

    private void deleteChild(Comment comment) {
        Comment parent = comment.getParent();
        parent.deleteChild(comment);
        commentRepository.delete(comment);
        if (parent.hasNoReply() && parent.isSoftRemoved()) {
            commentRepository.delete(parent);
        }
    }
}
