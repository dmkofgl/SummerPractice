package com.books.services;

import com.books.services.abstracts.EncryptServiceable;
import org.apache.commons.codec.digest.DigestUtils;

public class EncryptService implements EncryptServiceable {
    private static final EncryptService INSTANCE = new EncryptService();

    public static EncryptService getInstance() {
        return INSTANCE;
    }

    private EncryptService() {
    }

    @Override
    public String md5Encrypt(String source) {
        String encryptResult = DigestUtils.md5Hex(source);
        return encryptResult;
    }
}
