package com.spring.demo.dseerutt.service.impl;

import com.spring.demo.dseerutt.dto.item.SaleComputerDto;
import com.spring.demo.dseerutt.dto.item.SaleDto;
import com.spring.demo.dseerutt.dto.mapper.SaleDtoMapper;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.model.object.Sale;
import com.spring.demo.dseerutt.model.utils.Utils;
import com.spring.demo.dseerutt.repository.ComputerRepository;
import com.spring.demo.dseerutt.repository.SaleRepository;
import com.spring.demo.dseerutt.service.SaleService;
import com.spring.demo.dseerutt.service.validator.SaleDtoValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    private static final Logger LOGGER = LogManager.getLogger(SaleServiceImpl.class);

    @Autowired
    private SaleDtoValidator saleDtoValidator;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ComputerRepository computerRepository;
    private SaleDtoMapper mapper = Mappers.getMapper(SaleDtoMapper.class);

    @Override
    public SaleDto getSale(int id) {
        return mapper.saleToSaleDto(saleRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Sale not found " + id;
                    LOGGER.error(message);
                    return new ComputerNotFoundException(message);
                }));
    }

    @Override
    public List<SaleDto> getAllSales() {
        return saleRepository.findAll().stream().map(sale -> mapper.saleToSaleDto(sale)).collect(Collectors.toList());
    }

    @Override
    public SaleDto addSale(SaleDto saleDto) {
        SaleComputerDto saleComputerDto = saleDtoValidator.validatePost(saleDto);
        Computer computer = saleComputerDto.getComputer();
        Sale sale = mapper.saleDtoToSale(saleDto);
        sale.setComputer(computer);
        return mapper.saleToSaleDto(saleRepository.save(sale));
    }

    @Override
    public void deleteSale(int id) {
        saleRepository.deleteById(id);
    }

    public SaleDto updateSale(SaleDto saleDto) {
        SaleComputerDto saleComputerDto = saleDtoValidator.validatePut(saleDto);
        Sale sale = saleComputerDto.getSale();
        sale.setSaleDate(Utils.parseDate(saleDto.getSaleDate()));
        sale.setClientName(saleDto.getClientName());
        sale.setQuantity(saleDto.getQuantity());
        sale.setSalesman(saleDto.getSalesman());
        sale.setComputer(saleComputerDto.getComputer());
        Sale savedElement = saleRepository.save(sale);
        return mapper.saleToSaleDto(savedElement);
    }
}
