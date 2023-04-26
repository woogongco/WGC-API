package com.wgc.wgcapi.Post.Entity;
/*
Created on 2023/04/12 11:55 PM In Intelli J IDEA 
by jeon-wangi
*/

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category", catalog = "wgc")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "key")
    private String key;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private Set<Post> posts = new HashSet<>();


    public Set<Post> getPosts() {
        return posts;
    }
}
