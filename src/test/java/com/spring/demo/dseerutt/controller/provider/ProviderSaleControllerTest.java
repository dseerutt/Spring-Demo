package com.spring.demo.dseerutt.controller.provider;

import com.spring.demo.dseerutt.dto.item.client.SaleDto;
import com.spring.demo.dseerutt.service.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProviderSaleController.class)
@Import(ProviderSaleController.class)
class ProviderSaleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SaleService saleService;

    private SaleDto saleDto;
    private List<SaleDto> saleDtoList;

    private static final String SALE_JSON = """
            {
                "id": 3,
                "clientName": "Ali Gator",
                "salesman": "Charles Attan",
                "computerBrand": "Windows",
                "computerVersion": "Windows 11",
                "saleDate": "25-06-2008"
            }
            """;


    @BeforeEach
    void setup() {
        saleDtoList = new ArrayList<>();
        saleDto = new SaleDto();
        saleDto.setId(3);
        saleDto.setClientName("Ali Gator");
        saleDto.setSalesman("Charles Attan");
        saleDto.setComputerVersion("Windows 11");
        saleDto.setComputerBrand("Windows");
        saleDto.setQuantity(1);
        saleDto.setSaleDate("25-06-2008");
    }

    /**
     * updateSale test with nominal case, status is OK
     *
     * @throws Exception
     */
    @Test
    void updateSaleOkTest() throws Exception {
        when(saleService.updateSale(any())).thenReturn(saleDto);

        mvc.perform(put("/provider/sale")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(SALE_JSON)
                        .accept("application/json"))
                .andExpect(status().isOk());
    }

    /**
     * removeSale test - remove sale when everything is OK
     * No content is returned
     *
     * @throws Exception
     */
    @Test
    void removeSaleOkTest() throws Exception {
        mvc.perform(delete("/provider/sale/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(SALE_JSON)
                        .accept("application/json"))
                .andExpect(status().isNoContent());
    }

    /**
     * getAllSales test when there is a sale found - the sale json is retrieved
     *
     * @throws Exception never happens
     */
    @Test
    void getAllComputersNotEmptyTest() throws Exception {
        saleDtoList.add(saleDto);
        when(saleService.getAllSales(any())).thenReturn(saleDtoList);

        mvc.perform(get("/provider/sale").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[%s]".formatted(SALE_JSON)));
    }

    /**
     * getAllSales test when no result is found - empty list is returned
     *
     * @throws Exception never happens
     */
    @Test
    void getAllSalesEmptyTest() throws Exception {
        when(saleService.getAllSales(any())).thenReturn(saleDtoList);

        mvc.perform(get("/provider/sale").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }
}