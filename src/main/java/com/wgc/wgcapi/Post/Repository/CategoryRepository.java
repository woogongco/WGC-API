package com.wgc.wgcapi.Post.Repository;
/*
Created on 2023/04/28 11:28 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wgc.wgcapi.Post.Entity.QCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final JPAQueryFactory query;
    private final QCategory category = QCategory.category;
}
