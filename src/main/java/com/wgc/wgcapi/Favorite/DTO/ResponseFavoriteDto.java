package com.wgc.wgcapi.Favorite.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseFavoriteDto {
    private int count;

    public ResponseFavoriteDto(int count) {
        this.count = count;

    }
}
