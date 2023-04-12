package com.wgc.wgcapi.Post.Repository;
/*
Created on 2023/04/13 12:16 AM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Post.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PostDataJpaRepository extends JpaRepository<Post, Long>, CrudRepository<Post, Long> {

}
