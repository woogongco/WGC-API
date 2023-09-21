package com.wgc.wgcapi.Comment.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseComments {
    private List<ResponseComment> comments;
    private int count;

    public ResponseComments(List<ResponseComment> comments, int count) {
        this.comments = comments;
        this.count = count;
    }
}