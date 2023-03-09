package com.wgc.wgcapi.Member.Entity;
/*
Created on 2023/03/09 10:27 PM In Intelli J IDEA 
by jeon-wangi
*/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "mail")
    private String mail;

    @Column(name = "password")
    private String password;

    @Column(name = "permission")
    private String permission;

    @Column(name = "register_dt")
    private LocalDateTime registerDateTime;
}
