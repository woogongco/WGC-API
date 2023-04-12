package com.wgc.wgcapi.Post.Entity;
/*
Created on 2023/04/12 11:57 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Post.DTO.WritePostDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post", catalog = "wgc")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "`like`")
    private Long like = 0L;

    @Column(name = "view")
    private Long view = 0L;

    @Column(name = "register_date")
    @CreatedDate
    private LocalDateTime registerDate;

    public Post(Member member, Category category, WritePostDto writePostDto) {
        this.writer = member;
        this.category = category;
        this.title = writePostDto.getTitle();
        this.content = writePostDto.getContent();
    }
}
