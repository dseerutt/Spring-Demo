package com.spring.demo.dseerutt.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IndexServiceImplTest {

    /**
     * Test IndexServiceImpl.getIndex()
     * Port is properly inserted into html file
     */
    @Test
    public void getIndexTest() {
        // init
        String port = "8888";
        IndexServiceImpl toTest = new IndexServiceImpl();
        ReflectionTestUtils.setField(toTest, "port", port);

        // tests
        String resultIndex = toTest.getIndex();
        assertTrue(StringUtils.isNotBlank(resultIndex));
        assertTrue(resultIndex.contains("<li>GET <a href=\"localhost:" + port + "\">localhost:" + port + "</a></li>"));
    }

}