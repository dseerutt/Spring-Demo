package com.spring.demo.dseerutt.controller.client;

import com.spring.demo.dseerutt.dto.item.client.SaleDto;
import com.spring.demo.dseerutt.model.exception.SaleNotFoundException;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.exception.computer.EmptyStoreException;
import com.spring.demo.dseerutt.model.exception.server.ValidationException;
import com.spring.demo.dseerutt.service.SaleService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ClientSaleController.class)
class ClientSaleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SaleService saleService;
    private SaleDto saleDto;

    private static final String SALE_JSON_INPUT = """
            {
                "clientName": "Ali Gator",
                "salesman": "Charles Attan",
                "computerBrand": "Windows",
                "computerVersion": "Windows 11",
                "quantity": 1,
                "saleDate": "25-06-2008"
            }
            """;

    @BeforeEach
    void setup() {
        saleDto = new SaleDto();
        saleDto.setClientName("Ali Gator");
        saleDto.setSalesman("Charles Attan");
        saleDto.setComputerVersion("Windows 11");
        saleDto.setComputerBrand("Windows");
        saleDto.setQuantity(1);
        saleDto.setSaleDate("25-06-2008");
    }

    /**
     * addSale test with nominal case
     * status is OK, json is retrieved
     *
     * @throws Exception
     */
    @Test
    void addSaleOkTest() throws Exception {
        when(saleService.addSale(any())).thenReturn(saleDto);

        mvc.perform(post("/client/sale")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(SALE_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(SALE_JSON_INPUT));
    }

    /**
     * addSale test with Computer Not Found Exception
     * Status is not found
     *
     * @throws Exception
     */
    @Test
    void addSaleComputerNotFoundTest() throws Exception {
        when(saleService.addSale(any())).thenThrow(new ComputerNotFoundException(StringUtils.EMPTY));

        mvc.perform(post("/client/sale")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(SALE_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isNotFound());
    }

    /**
     * addSale test with Validation Exception
     * Status is bad request
     *
     * @throws Exception
     */
    @Test
    void addSaleValidationExceptionTest() throws Exception {
        when(saleService.addSale(any())).thenThrow(new ValidationException(StringUtils.EMPTY));

        mvc.perform(post("/client/sale")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(SALE_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isBadRequest());
    }

    /**
     * addSale test with Empty Store
     * Status is bad request
     *
     * @throws Exception
     */
    @Test
    void addSaleEmptyStoreExceptionTest() throws Exception {
        when(saleService.addSale(any())).thenThrow(new EmptyStoreException(StringUtils.EMPTY));

        mvc.perform(post("/client/sale")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(SALE_JSON_INPUT)
                        .accept("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllSalesOkTest() {
    }

    /**
     * getSale test with OK test, sale is returned
     *
     * @throws Exception never happens
     */
    @Test
    void getSaleOkTest() throws Exception {
        int saleId = 1;
        when(saleService.getSale(saleId)).thenReturn(saleDto);

        mvc.perform(get("/client/sale/%s".formatted(saleId)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(SALE_JSON_INPUT));
    }

    /**
     * getSale test when the sale is not found - 404 is returned
     *
     * @throws Exception never happens
     */
    @Test
    void getSaleFoundTest() throws Exception {
        int saleId = 1;
        when(saleService.getSale(saleId)).thenThrow(new SaleNotFoundException(StringUtils.EMPTY));

        mvc.perform(get("/client/sale/%s".formatted(saleId)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}