package com.spring.demo.dseerutt.security;

import com.spring.demo.dseerutt.controller.IndexController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class ApiKeyGenerator {
    private static final Logger LOGGER = LogManager.getLogger(IndexController.class);
    private static final String MD5 = "MD5";
    private static final String MD5_GENERATION_ERROR = "Failed to create api key md5 hash";
    private static final String BYTE_FORMAT = "%02x";

    /**
     * Create md5sum api key from username
     *
     * @param userName String
     * @return the apikey
     */
    public String generateMD5Hashvalue(String userName) {
        long date = System.currentTimeMillis();

        MessageDigest md;
        try {
            md = MessageDigest.getInstance(MD5);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(MD5_GENERATION_ERROR, e);
            throw new IllegalArgumentException(MD5_GENERATION_ERROR, e);
        }
        String secretPhase = "springDemo";
        byte[] hashResult = md.digest((date + userName + secretPhase).getBytes(UTF_8));
        // convert the value to hex
        String apiKey = bytesToHex(hashResult);
        return apiKey;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format(BYTE_FORMAT, b));
        }
        return sb.toString();
    }
}
