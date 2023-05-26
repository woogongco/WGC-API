package com.wgc.wgcapi.Member.Service;
/*
Created on 2023/03/22 11:42 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Authentication.Service.AuthenticationService;
import com.wgc.wgcapi.Authentication.Service.JwtService;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Common.Utils.EncryptUtils;
import com.wgc.wgcapi.Member.DTO.ModifyMemberInformationDto;
import com.wgc.wgcapi.Member.DTO.SignInUserDto;
import com.wgc.wgcapi.Member.DTO.SignUpUserDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Member.Repository.MemberRepository;
import com.wgc.wgcapi.Member.Repository.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
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

    public ResponseDto modifyIntroduction(HttpServletRequest request, Map<String, String> param) {
        Member member = getMemberInfo(request);
        if (Objects.isNull(member))
            return new ResponseDto(HttpStatus.BAD_REQUEST, "Invalid Member Information !");

        Member findMember = memberRepository.findById(member.getId()).get();
        findMember.updateIntroduction(param);

        HttpStatus status = HttpStatus.CREATED;
        return new ResponseDto(status, status.getReasonPhrase());
    }

    private Member getMemberInfo(HttpServletRequest request) {
        return (Member) request.getAttribute("claim");
    }

    public ResponseDto getMyInformation(HttpServletRequest request) {
        Member myInformation = this.getMemberInfo(request);
        if (Objects.isNull(myInformation))
            return new ResponseDto(HttpStatus.NOT_FOUND, "Information not found !");

        return new ResponseDto(HttpStatus.OK, myInformation.asDto());
    }

    public ResponseDto modifyInformation(HttpServletRequest request, ModifyMemberInformationDto dto) {
        try {
            Member member = this.getMemberInfo(request);
            Member findMember = memberRepository.findById(member.getId()).get();
            findMember.updateInformation(dto);
        } catch (Exception e) {
            log.error("modifyInformation error ! {}", e.getMessage());
            return new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
        return new ResponseDto(HttpStatus.OK);
    }
}