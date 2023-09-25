package com.wgc.wgcapi.Member.Entity;
/*
Created on 2023/03/09 10:27 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.Utils.EncryptUtils;
import com.wgc.wgcapi.Member.DTO.MemberDto;
import com.wgc.wgcapi.Member.DTO.ModifyMemberInformationDto;
import com.wgc.wgcapi.Post.Entity.Post;
import com.wgc.wgcapi.Post.Entity.PostLike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "mail")
    private String mail;

    @Column(name = "skil")
    private String skil;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "password")
    private String password;

    @Column(name = "github")
    private String github;

    @Column(name = "color")
    private String color;

    @Column(name = "permission")
    private String permission = "MEMBER";

    @Column(name = "register_dt")
    @CreatedDate
    private LocalDateTime registerDateTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "writer")
    private Set<Post> posts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private Set<PostLike> postLikes = new HashSet<>();

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "phone_number")
    private String phoneNumber;

    public Member(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public MemberDto asDto() {
        return new MemberDto(this);
    }

    public void updateIntroduction(Map<String, String> param) {
        this.skil = param.get("skill");
        this.introduction = param.get("introduction");
        this.color = param.get("color");
    }

    public void updateInformation(ModifyMemberInformationDto dto) {
        this.name = modifyIfNotEqual(this.name, dto.getName());
        this.skil = modifyIfNotEqual(this.skil, dto.getSkill());
        this.color = modifyIfNotEqual(this.color, dto.getColor());
        this.introduction = modifyIfNotEqual(this.introduction, dto.getIntroduction());
        if (Objects.nonNull(dto.getPassword()))
            this.password = modifyIfNotEqual(this.password, EncryptUtils.encrypt(dto.getPassword()));
        this.github = modifyIfNotEqual(this.github, dto.getGithub());
        this.profileImage = modifyIfNotEqual(this.profileImage, dto.getProfileImage());
        this.phoneNumber = modifyIfNotEqual(this.phoneNumber, dto.getPhoneNumber());
    }

    private String modifyIfNotEqual(String original, String input) {
        if (input == null || input.isEmpty() || input.isBlank())
            return original;

        return input;
    }
}
