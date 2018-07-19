package com.books.services.impl;

import com.books.services.abstracts.EncryptService;
import org.apache.commons.codec.digest.DigestUtils;


public class EncryptServiceImpl implements EncryptService {

    @Override
    public String md5Encrypt(String source) {
        String encryptResult = DigestUtils.md5Hex(source);
        return encryptResult;
    }
}
