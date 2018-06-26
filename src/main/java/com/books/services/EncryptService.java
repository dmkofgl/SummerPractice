package com.books.services;

import org.apache.commons.codec.digest.DigestUtils;

public class EncryptService {


    private static final EncryptService INSTANCE = new EncryptService();

    public static EncryptService getInstance() {
        return INSTANCE;
    }

    private EncryptService() {
    }

    public static String md5Encrypt(String source) {
        String encryptResult = DigestUtils.md5Hex(source);
        return encryptResult;
    }
}
