package com.wgc.wgcapi.Neighbor.DTO;
/*
Created on 2023/06/26 10:39 PM In Intelli J IDEA 
by jeon-wangi
*/

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NeighborDto {

    private Long memberId;
    private String name;
    private String mail;
    private Character accepted;
    private LocalDateTime requestDateTime;
}
