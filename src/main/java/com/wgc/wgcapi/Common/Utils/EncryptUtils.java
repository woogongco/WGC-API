package com.wgc.wgcapi.Common.Utils;
/*
Created on 2023/03/07 11:50 PM In Intelli J IDEA 
by jeon-wangi
*/

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
@Slf4j
public class EncryptUtils {
    public String encrypt(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("input is not present !");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            return DatatypeConverter.printHexBinary(md.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("EncryptUtils Exception = {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
