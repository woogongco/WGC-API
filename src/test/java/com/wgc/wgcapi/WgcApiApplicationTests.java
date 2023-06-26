package com.wgc.wgcapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.print.DocFlavor;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

//@SpringBootTest
class WgcApiApplicationTests {

    @Test
    @DisplayName("MD5 String test")
    void contextLoads() throws NoSuchAlgorithmException {
        String input = "test";
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        String result = DatatypeConverter.printHexBinary(md.digest());
        Assertions.assertEquals("098F6BCD4621D373CADE4E832627B4F6", result);
    }

}
