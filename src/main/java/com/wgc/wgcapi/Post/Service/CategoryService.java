package com.wgc.wgcapi.Post.Service;
/*
Created on 2023/04/28 11:11 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Post.Entity.Category;
import com.wgc.wgcapi.Post.Repository.CategoryDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryService {

    private final CategoryDataRepository categoryDataRepository;

    public List<Category> getCategory(){
        return this.categoryDataRepository.findAll();
    }
}
