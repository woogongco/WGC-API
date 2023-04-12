package com.wgc.wgcapi.Post.Entity;
/*
Created on 2023/04/12 11:57 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Member.Entity.Member;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post", catalog = "wgc")
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

    @Column(name = "like")
    private Long like;

    @Column(name = "view")
    private Long view;

    @Column(name = "register_date")
    @CreatedDate
    private LocalDateTime registerDate;
}
