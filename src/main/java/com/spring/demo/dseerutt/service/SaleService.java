package com.spring.demo.dseerutt.service;

import com.spring.demo.dseerutt.dto.item.client.SaleDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SaleService {

    SaleDto getSale(int id);

    List<SaleDto> getAllSales(Pageable pageable);

    SaleDto addSale(SaleDto saleDto);

    SaleDto updateSale(SaleDto saleDto);

    void deleteSale(int id);
}
