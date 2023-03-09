package com.wgc.wgcapi.Authentication.Service;
/*
Created on 2023/03/09 10:23 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Member.DTO.MemberDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Member.Repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final String encryptKey = "wgc-api-jwt-sign-cd1b0b0f-be7d-11ed-b6ef-064f53adf942";
    private final SignatureAlgorithm sign = SignatureAlgorithm.HS512;
    private byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(this.encryptKey);
    private final Key signKey = new SecretKeySpec(this.secretKeyBytes, sign.getJcaName());
    private final Date expireDate = new Date(System.currentTimeMillis() + (2592000000L * 12 * 3));

    private final MemberRepository memberRepository;

    public String generate(MemberDto member) {
        return Jwts.builder()
                .claim("id", member.getId())
                .claim("name", member.getName())
                .claim("mail", member.getMail())
                .claim("register", member.getRegisterDateTime().toString())
                .signWith(this.signKey, this.sign)
                .setExpiration(this.expireDate)
                .compact();
    }

    public Member validate(String token) {
        Claims claims = this.getClaim(token);
        Long memberId = Long.parseLong(String.valueOf(claims.get("id")));
        return memberRepository.find(memberId);
    }

    private Claims getClaim(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.signKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
