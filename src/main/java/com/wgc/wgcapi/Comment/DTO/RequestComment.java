package com.wgc.wgcapi.Comment.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wgc.wgcapi.Comment.Entity.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestComment {

    private String content;

    public RequestComment(Comment comment) {
        this.content = comment.getContent();
    }
}
