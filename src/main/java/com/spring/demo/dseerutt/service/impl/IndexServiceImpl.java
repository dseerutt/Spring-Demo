package com.spring.demo.dseerutt.service.impl;

import com.spring.demo.dseerutt.service.IndexService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class IndexServiceImpl implements IndexService {
    private static final Logger LOGGER = LogManager.getLogger(IndexServiceImpl.class);
    private static final String INDEX_FILE_NAME = "/index.html";
    private static final String PORT_TOKEN = "%PORT%";

    @Value("${server.port}")
    private String port;

    private String readIndexFile() {
        try {
            InputStream inputStream = getClass().getResourceAsStream(INDEX_FILE_NAME);
            if (inputStream != null && port != null) {
                return new String(inputStream.readAllBytes()).replace(PORT_TOKEN, port);
            } else {
                LOGGER.error("Failed to read index file");
            }
        } catch (IOException e) {
            LOGGER.error("Failed to read index file");
        }
        return StringUtils.EMPTY;
    }

    @Override
    public String getIndex() {
        LOGGER.info("Index is called");
        return readIndexFile();
    }
}
