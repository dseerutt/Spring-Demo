package com.spring.demo.dseerutt.service.validator;

import com.spring.demo.dseerutt.dto.item.client.SaleComputerDto;
import com.spring.demo.dseerutt.dto.item.client.SaleDto;
import com.spring.demo.dseerutt.model.exception.SaleNotFoundException;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.exception.computer.EmptyStoreException;
import com.spring.demo.dseerutt.model.exception.server.ValidationException;
import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.model.object.ComputerStore;
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
        if (StringUtils.isNotBlank(clientName) && clientName.length() >= 255)
            throw new ValidationException("Client name is limited to 255 characters");
        String salesman = saleDto.getSalesman();
        if (StringUtils.isNotBlank(salesman) && salesman.length() >= 50)
            throw new ValidationException("Salesman is limited to 50 characters");
        if (saleDto.getQuantity() <= 0)
            throw new ValidationException("Sale quantity cannot be 0 or negative");
        Utils.parseDate(saleDto.getSaleDate());
    }

    public SaleComputerDto validatePost(SaleDto saleDto) {
        if (saleDto.getId() != 0)
            throw new ValidationException("Id cannot be set when using POST");
        validate(saleDto);
        SaleComputerDto saleComputerDto = initSaleComputerDto(saleDto);
        Computer computer = saleComputerDto.getComputer();
        ComputerStore computerStore = computer.getComputerStore();
        if (computerStore.getStock() < 1)
            throw new EmptyStoreException("Not enough stock to buy computer with id " + computer.getId());
        return saleComputerDto;
    }

    private SaleComputerDto initSaleComputerDto(SaleDto saleDto) {
        SaleComputerDto saleComputerDto = new SaleComputerDto();
        saleComputerDto.setComputer(computerRepository.findByBrandAndVersion(saleDto.getComputerBrand(), saleDto.getComputerVersion())
                .orElseThrow(() -> {
                    String message = "Computer not found with brand " + saleDto.getComputerBrand() + " and version " + saleDto.getComputerVersion();
                    LOGGER.error(message);
                    return new ComputerNotFoundException(message);
                }));
        return saleComputerDto;
    }

    public SaleComputerDto validatePut(SaleDto saleDto) {
        // Do not update stock for modification of a sale
        if (saleDto.getId() == 0)
            throw new ValidationException("Computer Id has to be related to an existing computer when using POST");
        validate(saleDto);
        SaleComputerDto saleComputerDto = initSaleComputerDto(saleDto);
        saleComputerDto.setSale(saleRepository.findById(saleDto.getId())
                .orElseThrow(() -> {
                    String message = "Sale with id " + saleDto.getId() + " was not found";
                    LOGGER.error(message);
                    return new SaleNotFoundException(message);
                }));
        return saleComputerDto;
    }
}
