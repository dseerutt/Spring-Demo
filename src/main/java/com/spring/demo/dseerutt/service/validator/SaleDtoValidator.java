package com.spring.demo.dseerutt.service.validator;

import com.spring.demo.dseerutt.dto.item.SaleComputerDto;
import com.spring.demo.dseerutt.dto.item.SaleDto;
import com.spring.demo.dseerutt.model.exception.SaleNotFoundException;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.exception.server.ValidationException;
import com.spring.demo.dseerutt.model.utils.Utils;
import com.spring.demo.dseerutt.repository.ComputerRepository;
import com.spring.demo.dseerutt.repository.SaleRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleDtoValidator {
    private static final Logger LOGGER = LogManager.getLogger(SaleDtoValidator.class);

    @Autowired
    private ComputerRepository computerRepository;
    @Autowired
    private SaleRepository saleRepository;

    public void validate(SaleDto saleDto) {
        String clientName = saleDto.getClientName();
        if (StringUtils.isNotBlank(clientName) && clientName.length() >= 255) {
            throw new ValidationException("Client name is limited to 255 characters");
        }
        String salesman = saleDto.getSalesman();
        if (StringUtils.isNotBlank(salesman) && salesman.length() >= 50) {
            throw new ValidationException("Salesman is limited to 50 characters");
        }
        if (saleDto.getQuantity() <= 0) {
            throw new ValidationException("Sale quantity cannot be 0 or negative");
        }
        Utils.parseDate(saleDto.getSaleDate());
    }

    public SaleComputerDto validatePost(SaleDto saleDto) {
        SaleComputerDto saleComputerDto = new SaleComputerDto();
        if (saleDto.getId() != 0) {
            throw new ValidationException("Id cannot be set when using POST");
        }
        saleComputerDto.setComputer(computerRepository.findByBrandAndVersion(saleDto.getComputerBrand(), saleDto.getComputerVersion())
                .orElseThrow(() -> {
                    String message = "Computer not found " + saleDto.getComputerBrand() + " " + saleDto.getComputerVersion();
                    LOGGER.error(message);
                    return new ComputerNotFoundException(message);
                }));
        validate(saleDto);
        return saleComputerDto;
    }

    public SaleComputerDto validatePut(SaleDto saleDto) {
        if (saleDto.getId() == 0) {
            throw new ValidationException("Computer Id has to be related to an existing computer when using POST");
        }
        SaleComputerDto saleComputerDto = new SaleComputerDto();
        validate(saleDto);
        saleComputerDto.setComputer(computerRepository.findByBrandAndVersion(saleDto.getComputerBrand(), saleDto.getComputerVersion())
                .orElseThrow(() -> {
                    String message = "Computer not found with brand " + saleDto.getComputerBrand() + " and version " + saleDto.getComputerVersion();
                    LOGGER.error(message);
                    return new ComputerNotFoundException(message);
                }));
        saleComputerDto.setSale(saleRepository.findById(saleDto.getId())
                .orElseThrow(() -> {
                    String message = "Sale with id " + saleDto.getId() + " was not found";
                    LOGGER.error(message);
                    return new SaleNotFoundException(message);
                }));
        return saleComputerDto;
    }
}
