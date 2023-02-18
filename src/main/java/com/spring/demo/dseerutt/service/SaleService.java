package com.spring.demo.dseerutt.service;

import com.spring.demo.dseerutt.dto.item.SaleDto;

import java.util.List;

public interface SaleService {

    SaleDto getSale(int id);

    List<SaleDto> getAllSales();

    SaleDto addSale(SaleDto saleDto);

    SaleDto updateSale(SaleDto saleDto);

    void deleteSale(int id);
}
