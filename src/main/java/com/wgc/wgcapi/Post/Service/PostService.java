package com.wgc.wgcapi.Post.Service;
/*
Created on 2023/04/12 11:54 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Post.DTO.WritePostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    public ResponseDto writePost(WritePostDto dto) {

        return new ResponseDto();
    }

}
