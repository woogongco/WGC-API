package com.wgc.wgcapi.Post.Repository;
/*
Created on 2023/04/13 12:16 AM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Post.Entity.Category;
import com.wgc.wgcapi.Post.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostDataJpaRepository extends JpaRepository<Post, Long>, CrudRepository<Post, Long> {

    Post findPostByIdAndIsDeleteEquals(Long id, Character isDelete);

    List<Post> findTop5ByCategoryIsAndIsDeleteEqualsOrderByIdDesc(Category category, Character isDelete);

    List<Post> findPostsByWriterIdAndIsDeleteIsOrderByRegisterDateDesc(Long writerId, Character isDelete);
}
