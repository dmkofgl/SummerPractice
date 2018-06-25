package com.books.services;

import org.apache.commons.codec.digest.DigestUtils;

public class EncryptService {
    public static String md5Encrypt(String source) {
        String encryptResult = DigestUtils.md5Hex(source);
        return encryptResult;
    }
}
