package com.spring.demo.dseerutt.controller.provider;

import com.spring.demo.dseerutt.dto.item.client.ComputerDto;
import com.spring.demo.dseerutt.dto.item.client.ProvisionDto;
import com.spring.demo.dseerutt.model.exception.computer.ComputerAlreadyExistsException;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.exception.server.ValidationException;
import com.spring.demo.dseerutt.service.ComputerService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProviderComputerController.class)
@Import(ProviderComputerController.class)
class ProviderComputerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ComputerService computerService;

    private ComputerDto computerDto;
    private ProvisionDto provisionDto;
    private static final String COMPUTER_JSON_INPUT = """
            {
                "brand": "Arc8 linux",
                "version": "Version 41",
                "description": "Added on test",
                "price": 29.90
            }
            """;
    private static final String COMPUTER_JSON_RESULT = """
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
        computerDto.setId(6);
        computerDto.setPrice(29.90);
        computerDto.setBrand("Arc8 linux");
        computerDto.setDescription("Added on test");
        computerDto.setStock(0);
        computerDto.setLastProvisionDate(null);
        computerDto.setVersion("Version 41");

        provisionDto = new ProvisionDto();
        provisionDto.setId(1);
        provisionDto.setQuantity(4);
    }

    /**
     * AddComputer test with nominal case
     * Status is created and json is retrieved
     *
     * @throws Exception never happens
     */
    @Test
    void addComputerOkTest() throws Exception {
        when(computerService.addComputer(any())).thenReturn(computerDto);

        mvc.perform(post("/provider/computer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(COMPUTER_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(COMPUTER_JSON_RESULT));
    }

    /**
     * AddComputer test with case where computer already exists
     * Status is Bad Request
     *
     * @throws Exception never happens
     */
    @Test
    void addComputerTestComputerAlreadyExists() throws Exception {
        when(computerService.addComputer(any())).thenThrow(new ComputerAlreadyExistsException(StringUtils.EMPTY));

        mvc.perform(post("/provider/computer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(COMPUTER_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isBadRequest());
    }

    /**
     * AddComputer test with case where validation fails
     * Status is bad request
     *
     * @throws Exception never happens
     */
    @Test
    void addComputerTestValidationException() throws Exception {
        when(computerService.addComputer(any())).thenThrow(new ValidationException(StringUtils.EMPTY));

        mvc.perform(post("/provider/computer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(COMPUTER_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isBadRequest());
    }

    /**
     * UpdateComputer test with nominal case
     * Status is OK, json is retrieved
     *
     * @throws Exception never happens
     */
    @Test
    void updateComputerOkTest() throws Exception {
        when(computerService.updateComputer(any())).thenReturn(computerDto);

        mvc.perform(put("/provider/computer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(COMPUTER_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(COMPUTER_JSON_RESULT));
    }

    /**
     * UpdateComputer test with validation exception
     * Status is bad request
     *
     * @throws Exception never happens
     */
    @Test
    void updateComputerValidationExceptionTest() throws Exception {
        when(computerService.updateComputer(any())).thenThrow(new ValidationException(StringUtils.EMPTY));

        mvc.perform(put("/provider/computer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(COMPUTER_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isBadRequest());
    }

    /**
     * UpdateComputer test with ComputerNotFound exception
     * Status not found status is sent
     *
     * @throws Exception never happens
     */
    @Test
    void updateComputerComputerNotFoundTest() throws Exception {
        when(computerService.updateComputer(any())).thenThrow(new ComputerNotFoundException(StringUtils.EMPTY));

        mvc.perform(put("/provider/computer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(COMPUTER_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isNotFound());
    }

    /**
     * ProvisionComputer with nominal use case
     * status OK is found
     *
     * @throws Exception never happens
     */
    @Test
    void provisionComputerOkTest() throws Exception {
        when(computerService.provisionComputer(any())).thenReturn(provisionDto);

        mvc.perform(post("/provider/computer/provision")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(COMPUTER_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isOk());
    }

    /**
     * ProvisionComputer with computer not found exception use case
     * Status is not found
     *
     * @throws Exception never happens
     */
    @Test
    void provisionComputerComputerNotFoundTest() throws Exception {
        when(computerService.provisionComputer(any())).thenThrow(new ComputerNotFoundException(StringUtils.EMPTY));

        mvc.perform(post("/provider/computer/provision")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(COMPUTER_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isNotFound());
    }

    /**
     * DeprovisionComputer with nominal use case
     * Status is OK
     *
     * @throws Exception never happens
     */
    @Test
    void deprovisionComputerOkTest() throws Exception {
        when(computerService.deprovisionComputer(any())).thenReturn(provisionDto);

        mvc.perform(post("/provider/computer/deprovision")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(COMPUTER_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isOk());
    }

    /**
     * DeprovisionComputer with computer not found exception use case
     * Status is not found
     *
     * @throws Exception never happens
     */
    @Test
    void deprovisionComputerComputerNotFoundTest() throws Exception {
        when(computerService.deprovisionComputer(any())).thenThrow(new ComputerNotFoundException(StringUtils.EMPTY));

        mvc.perform(post("/provider/computer/deprovision")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(COMPUTER_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isNotFound());
    }

    /**
     * UpdateComputer with nominal case
     * Status is OK
     *
     * @throws Exception never happens
     */
    @Test
    void updateComputerStatusOkTest() throws Exception {
        mvc.perform(patch("/provider/computer/1/status")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(COMPUTER_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isOk());
    }
}