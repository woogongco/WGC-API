package com.wgc.wgcapi.Comment.Entity;

import com.wgc.wgcapi.Comment.DTO.RequestComment;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Post.Entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @Column(name = "content")
    private String content;

    @Column(name = "register_date")
    @CreatedDate
    private LocalDateTime registerDate;

    @Column(name = "last_update")
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;

    @Column(name = "is_delete")
    private Character isDelete = 'N';

    @Builder
    public Comment(String content, Post post, Member writer, Comment parent) {
        this.content = content;
        this.post = post;
        this.writer = writer;
        this.parent = parent;
    }

    public static Comment parent(Member writer, Post post, String content) {
        return new Comment(content, post, writer, null);
    }

    public static Comment child(Member writer, Post post, String content, Comment parent) {
        Comment child = new Comment(content, post, writer, parent);
        parent.getChildren().add(child);
        return child;
    }

    @PrePersist
    private void onPrePersist() {
        this.registerDate = LocalDateTime.now();
    }

    public boolean isParent() {
        return Objects.isNull(parent);
    }

    public boolean isSoftRemoved() {

        return this.isDelete == 'Y';
    }

    public void edit(RequestComment dto) {
        this.content = dto.getContent();
    }

    public boolean hasNoReply() {
        return children.isEmpty();
    }

    public void changePretendingToBeRemoved() {
        this.isDelete = 'Y';
    }

    public void deleteChild(Comment reply) {
        this.children.remove(reply);
    }
}
