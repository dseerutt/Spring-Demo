package com.spring.demo.dseerutt.controller.client;

import com.spring.demo.dseerutt.dto.item.client.ComputerDto;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.service.ComputerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientComputerController.class)
class ClientComputerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ComputerService computerService;

    private ComputerDto computerDto;
    private List<ComputerDto> computerDtoList;
    private static final String COMPUTER_JSON = """
            {
                "id": 6,
                "brand": "Arc8 linux",
                "version": "Version 41",
                "description": "Added on test",
                "price": 29.90
            }
            """;

    @BeforeEach
    void setup() {
        computerDto = new ComputerDto();
        computerDto.setPrice(29.90);
        computerDto.setBrand("Arc8 linux");
        computerDto.setDescription("Added on test");
        computerDto.setId(6);
        computerDto.setStock(0);
        computerDto.setLastProvisionDate(null);
        computerDto.setVersion("Version 41");
        computerDtoList = new ArrayList<>();
    }

    /**
     * getComputer test when everything is OK - the computer json is returned
     *
     * @throws Exception never happens
     */
    @Test
    void getComputerOkTest() throws Exception {
        int computerId = 1;
        when(computerService.getComputer(computerId)).thenReturn(computerDto);

        mvc.perform(get("/client/computer/" + computerId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(COMPUTER_JSON));
    }

    /**
     * getComputer test when the computer is not found - 404 is returned
     *
     * @throws Exception never happens
     */
    @Test
    void getComputerNotFoundTest() throws Exception {
        int computerId = 1;
        when(computerService.getComputer(computerId)).thenThrow(new ComputerNotFoundException(""));

        mvc.perform(get("/client/computer/" + computerId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * getAllComputer test when there is a computer found - the computer json is retrieved
     *
     * @throws Exception never happens
     */
    @Test
    void getAllComputersNotEmptyTest() throws Exception {
        computerDtoList.add(computerDto);
        when(computerService.getAllComputers()).thenReturn(computerDtoList);

        mvc.perform(get("/client/computer").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + COMPUTER_JSON + "]"));
    }

    /**
     * getAllComputer test when no result is found - empty list is returned
     *
     * @throws Exception never happens
     */
    @Test
    void getAllComputersEmptyTest() throws Exception {
        when(computerService.getAllComputers()).thenReturn(computerDtoList);

        mvc.perform(get("/client/computer").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }
}