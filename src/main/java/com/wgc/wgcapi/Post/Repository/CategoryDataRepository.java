package com.wgc.wgcapi.Post.Repository;
/*
Created on 2023/04/13 12:25 AM In Intelli J IDEA 
by jeon-wangi
*/


import com.wgc.wgcapi.Post.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDataRepository extends JpaRepository<Category, Long> {

    Category findCategoryById(Long id);

    Category findByName(String name);

    List<Category> findAll();
}
