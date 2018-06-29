package com.books.services;

import com.books.services.abstracts.EncryptServiceable;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component("EncryptService")
public class EncryptService implements EncryptServiceable {

    @Override
    public String md5Encrypt(String source) {
        String encryptResult = DigestUtils.md5Hex(source);
        return encryptResult;
    }
}
