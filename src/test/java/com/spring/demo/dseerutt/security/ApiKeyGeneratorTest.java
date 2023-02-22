package com.spring.demo.dseerutt.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ApiKeyGeneratorTest {


    @Test
    void generateMD5HashvalueTest() {
        String username = "client";
        ApiKeyGenerator apiKeyGenerator = new ApiKeyGenerator();
        String firstHash = apiKeyGenerator.generateMD5Hashvalue(username);
        assertEquals(32, firstHash.length());
        String secondHash = apiKeyGenerator.generateMD5Hashvalue(username);
        assertEquals(32, secondHash.length());
        assertNotEquals(firstHash, secondHash);
    }

}