package com.spring.demo.dseerutt.service.impl;

import com.spring.demo.dseerutt.dto.item.client.SaleComputerDto;
import com.spring.demo.dseerutt.dto.item.client.SaleDto;
import com.spring.demo.dseerutt.model.exception.SaleNotFoundException;
import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.model.object.ComputerStore;
import com.spring.demo.dseerutt.model.object.Sale;
import com.spring.demo.dseerutt.repository.SaleRepository;
import com.spring.demo.dseerutt.service.validator.SaleDtoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaleServiceImplTest {

    @InjectMocks
    private SaleServiceImpl saleServiceImpl;

    @Mock
    private SaleRepository saleRepository;
    @Mock
    private SaleDtoValidator saleDtoValidator;

    private static final int SALE_ID = 1;
    private static final int SALE_ID2 = 2;
    private static final int QUANTITY = 2;
    private static final int QUANTITY2 = 6;
    private static final int STOCK = 10;
    private final static String COMPUTER_BRAND = "ComputerBrand";
    private final static String COMPUTER_VERSION = "ComputerVersion";
    private final static String COMPUTER_BRAND2 = "ComputerBrand2";
    private final static String COMPUTER_VERSION2 = "ComputerVersion2";
    private final static String STRING_DATE = "11-12-2023";
    private final static LocalDate DATE = LocalDate.of(2023, 12, 11);
    private SaleDto saleDto;
    private SaleDto saleDto2;
    private Sale sale;
    private Sale sale2;
    private Pageable pageable;
    private List<SaleDto> saleDtoList;
    private List<Sale> saleList;
    private Page<Sale> salePage;
    private SaleComputerDto saleComputerDto;
    private Computer computer;

    private Sale initSale(int id, Computer computer, int quantity) {
        var sale = new Sale();
        sale.setId(id);
        sale.setQuantity(quantity);
        sale.setComputer(computer);
        sale.setSaleDate(DATE);
        return sale;
    }

    private SaleDto initSaleDto(int saleId, int quantity, String computerVersion, String computerBrand) {
        var saleDto = new SaleDto();
        saleDto.setId(saleId);
        saleDto.setQuantity(quantity);
        saleDto.setComputerVersion(computerVersion);
        saleDto.setComputerBrand(computerBrand);
        saleDto.setSaleDate(STRING_DATE);
        return saleDto;
    }

    private Computer initComputer(String computerBrand, String computerVersion, int stock) {
        var computer = new Computer();
        computer.setBrand(computerBrand);
        computer.setVersion(computerVersion);
        ComputerStore computerStore = new ComputerStore();
        computerStore.setStock(stock);
        computer.setComputerStore(computerStore);
        return computer;
    }

    @BeforeEach
    void setupTest() {
        computer = initComputer(COMPUTER_BRAND, COMPUTER_VERSION, STOCK);
        var computer = initComputer(COMPUTER_BRAND, COMPUTER_VERSION, STOCK);
        var computer2 = initComputer(COMPUTER_BRAND2, COMPUTER_VERSION2, STOCK);
        sale = initSale(SALE_ID, computer, QUANTITY);
        sale2 = initSale(SALE_ID2, computer2, QUANTITY2);
        saleDto = initSaleDto(SALE_ID, QUANTITY, COMPUTER_VERSION, COMPUTER_BRAND);
        saleDto2 = initSaleDto(SALE_ID2, QUANTITY2, COMPUTER_VERSION2, COMPUTER_BRAND2);
        pageable = PageRequest.of(0, 2);
        saleDtoList = new ArrayList<>();
        saleList = new ArrayList<>();
        saleComputerDto = new SaleComputerDto();
        saleComputerDto.setComputer(computer);
        saleComputerDto.setSale(sale);
    }

    /**
     * getSale test with sale not found case -> SaleNotFoundException is thrown
     */
    @Test
    void getSaleSaleNotFoundExceptionTest() {
        when(saleRepository.findById(SALE_ID)).thenReturn(Optional.empty());
        SaleNotFoundException exception = assertThrows(
                SaleNotFoundException.class,
                () -> saleServiceImpl.getSale(SALE_ID),
                "Should throw SaleNotFoundException");
        assertEquals("Sale not found with id %s".formatted(SALE_ID), exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    /**
     * getSale test with nominal use case - sale is returned
     */
    @Test
    void getSaleOkTest() {
        when(saleRepository.findById(SALE_ID)).thenReturn(Optional.of(sale));
        assertEquals(saleDto, saleServiceImpl.getSale(SALE_ID));
    }

    /**
     * getAllSales with empty list result - empty list is returned
     */
    @Test
    void getAllSalesEmptyOkTest() {
        salePage = new PageImpl<>(saleList, pageable, 0);
        when(saleRepository.findAll(pageable)).thenReturn(salePage);
        assertEquals(saleDtoList, saleServiceImpl.getAllSales(pageable));
    }

    /**
     * getAllSales with non-empty list result - right list is returned
     */
    @Test
    void getAllSalesNonEmptyOkTest() {
        saleList.add(sale);
        saleList.add(sale2);
        saleDtoList.add(saleDto);
        saleDtoList.add(saleDto2);
        salePage = new PageImpl<>(saleList, pageable, 0);
        when(saleRepository.findAll(pageable)).thenReturn(salePage);
        assertEquals(saleDtoList, saleServiceImpl.getAllSales(pageable));
    }

    /**
     * addSale test with nominal case : stock is updated from the sale quantity
     */
    @Test
    void addSaleOkTest() {
        when(saleDtoValidator.validatePost(saleDto)).thenReturn(saleComputerDto);
        sale.getComputer().getComputerStore().setStock(STOCK - QUANTITY);
        when(saleRepository.save(sale)).thenReturn(sale);
        assertEquals(saleDto, saleServiceImpl.addSale(saleDto));
    }

    /**
     * deleteSale test with sale found, delete is carried out on database
     */
    @Test
    void deleteSaleWithDeleteOkTest() {
        when(saleDtoValidator.validateDelete(SALE_ID)).thenReturn(true);
        doNothing().when(saleRepository).deleteById(SALE_ID);
        saleServiceImpl.deleteSale(SALE_ID);
    }

    /**
     * deleteSale test with sale not found, delete is not carried out on database
     */
    @Test
    void deleteSaleWithoutDeleteOkTest() {
        when(saleDtoValidator.validateDelete(SALE_ID)).thenReturn(false);
        saleServiceImpl.deleteSale(SALE_ID);
    }

    /**
     * updateSale test with nominal test
     */
    @Test
    void updateSaleOkTest() {
        when(saleDtoValidator.validatePut(saleDto)).thenReturn(saleComputerDto);
        when(saleRepository.save(sale)).thenReturn(sale);
        assertEquals(saleDto, saleServiceImpl.updateSale(saleDto));
    }
}