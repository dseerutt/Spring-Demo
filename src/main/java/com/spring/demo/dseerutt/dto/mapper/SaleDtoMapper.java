package com.spring.demo.dseerutt.dto.mapper;

import com.spring.demo.dseerutt.dto.item.client.SaleDto;
import com.spring.demo.dseerutt.model.object.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SaleDtoMapper {

    @Mapping(source = "saleDate", target = "saleDate", dateFormat = "dd-MM-yyyy")
    Sale saleDtoToSale(SaleDto saleDto);

    @Mapping(source = "sale.computer.brand", target = "computerBrand")
    @Mapping(source = "sale.computer.version", target = "computerVersion")
    @Mapping(source = "saleDate", target = "saleDate", dateFormat = "dd-MM-yyyy")
    SaleDto saleToSaleDto(Sale sale);
}
