package com.wgc.wgcapi.Member.Service;
/*
Created on 2023/03/22 11:42 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Authentication.Service.AuthenticationService;
import com.wgc.wgcapi.Authentication.Service.JwtService;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Common.Utils.EncryptUtils;
import com.wgc.wgcapi.Member.DTO.MemberDto;
import com.wgc.wgcapi.Member.DTO.SignInUserDto;
import com.wgc.wgcapi.Member.DTO.SignUpUserDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Member.Repository.MemberRepository;
import com.wgc.wgcapi.Member.Repository.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRepositoryImpl memberRepositoryImpl;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public ResponseDto signUp(SignUpUserDto dto) {
        try {
            Member member = memberRepository.findByMail(dto.getMail());
            if (Objects.nonNull(member))
                return new ResponseDto(HttpStatus.BAD_REQUEST, "Member is already exist !");

            memberRepository.save(dto.asEntity());
            return new ResponseDto(HttpStatus.CREATED, HttpStatus.CREATED.getReasonPhrase());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public ResponseDto signIn(SignInUserDto dto) {
        String encryptPassword = EncryptUtils.encrypt(dto.getPassword());
        Member member = memberRepository.findMemberByMailIsAndPasswordIs(dto.getMail(), encryptPassword);
        if (Objects.isNull(member))
            return new ResponseDto(HttpStatus.BAD_REQUEST, "User is not found !");

        String token = authenticationService.getAuthenticationToken(member);
        return new ResponseDto(token);
    }

    public ResponseDto validateToken(String token) {
        Member member = this.jwtService.validate(token);
        MemberDto dto = member.asDto();
        return new ResponseDto(dto);
    }
}
