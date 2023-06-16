package com.wgc.wgcapi.Post.DTO;
/*
Created on 2023/06/16 10:57 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Post.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryDto {
    private Long id;
    private String name;
    private String key;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.key = category.getKey();
    }
}
