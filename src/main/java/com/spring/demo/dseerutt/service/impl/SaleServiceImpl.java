package com.spring.demo.dseerutt.service.impl;

import com.spring.demo.dseerutt.dto.item.client.SaleDto;
import com.spring.demo.dseerutt.dto.mapper.SaleDtoMapper;
import com.spring.demo.dseerutt.model.exception.SaleNotFoundException;
import com.spring.demo.dseerutt.model.utils.Utils;
import com.spring.demo.dseerutt.repository.SaleRepository;
import com.spring.demo.dseerutt.service.SaleService;
import com.spring.demo.dseerutt.service.validator.SaleDtoValidator;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    private final SaleDtoMapper mapper = Mappers.getMapper(SaleDtoMapper.class);

    @Override
    public SaleDto getSale(int id) {
        return mapper.saleToSaleDto(saleRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Sale not found with id " + id;
                    LOGGER.error(message);
                    return new SaleNotFoundException(message);
                }));
    }

    @Override
    public List<SaleDto> getAllSales(Pageable pageable) {
        return saleRepository.findAll(pageable).stream().map(mapper::saleToSaleDto).collect(Collectors.toList());
    }

    @Override
    public SaleDto addSale(SaleDto saleDto) {
        var saleComputerDto = saleDtoValidator.validatePost(saleDto);
        var computer = saleComputerDto.getComputer();
        var computerStore = computer.getComputerStore();
        computerStore.setStock(computerStore.getStock() - saleDto.getQuantity());
        var sale = mapper.saleDtoToSale(saleDto);
        sale.setComputer(computer);
        return mapper.saleToSaleDto(saleRepository.save(sale));
    }

    /**
     * deleteSale
     * delete if present, no error if not
     * sale id
     *
     * @param id
     */
    @Transactional
    @Override
    public void deleteSale(int id) {
        if (saleDtoValidator.validateDelete(id)) {
            LOGGER.info("Id " + id + " was found for sale deletion");
            saleRepository.deleteById(id);
        } else {
            LOGGER.info("Nothing to delete");
        }
    }

    public SaleDto updateSale(SaleDto saleDto) {
        // Do not update stock for modification of a sale
        var saleComputerDto = saleDtoValidator.validatePut(saleDto);
        var sale = saleComputerDto.getSale();
        String date = saleDto.getSaleDate();
        if (StringUtils.isNotBlank(date)) {
            sale.setSaleDate(Utils.parseDate(date));
        }
        sale.setClientName(saleDto.getClientName());
        sale.setQuantity(saleDto.getQuantity());
        sale.setSalesman(saleDto.getSalesman());
        sale.setComputer(saleComputerDto.getComputer());
        var savedElement = saleRepository.save(sale);
        return mapper.saleToSaleDto(savedElement);
    }
}
