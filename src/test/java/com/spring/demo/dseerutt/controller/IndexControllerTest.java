package com.spring.demo.dseerutt.controller;

import com.spring.demo.dseerutt.service.IndexService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IndexController.class)
public class IndexControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IndexService indexService;

    /**
     * Test getIndex();
     * Service call is properly done
     */
    @Test
    public void getIndexTest() throws Exception {
        String htmlResult = "HTMLResult";
        when(indexService.getIndex()).thenReturn(htmlResult);

        mvc.perform(get("/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE))
                .andExpect(content().string(htmlResult));
    }

}