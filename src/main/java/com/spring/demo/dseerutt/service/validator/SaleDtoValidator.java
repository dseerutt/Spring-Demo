package com.spring.demo.dseerutt.service.validator;

import com.spring.demo.dseerutt.dto.item.client.SaleComputerDto;
import com.spring.demo.dseerutt.dto.item.client.SaleDto;
import com.spring.demo.dseerutt.model.exception.SaleNotFoundException;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.exception.computer.EmptyStoreException;
import com.spring.demo.dseerutt.model.exception.server.DateParsingException;
import com.spring.demo.dseerutt.model.exception.server.ValidationException;
import com.spring.demo.dseerutt.model.utils.Utils;
import com.spring.demo.dseerutt.repository.ComputerRepository;
import com.spring.demo.dseerutt.repository.SaleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleDtoValidator {
    private static final String CLIENT_NAME_SIZE_MESSAGE = "Client name is limited to 255 characters";
    private static final String SALESMAN_SIZE_MESSAGE = "Salesman is limited to 50 characters";
    private static final String NEGATIVE_QUANTITY_MESSAGE = "Sale quantity cannot be 0 or negative";
    private static final String EMPTY_SALE_DATE_MESSAGE = "Sale date cannot be empty";
    private static final String WRONG_FORMAT_SALE_DATE_MESSAGE = "Failed to parse saleDate %s";
    private static final String POST_ID_SET_MESSAGE = "Id cannot be set when using POST";
    private static final String PUT_ID_NOT_SET_MESSAGE = "Sale Id cannot be 0 using PUT";
    private static final String NO_STOCK_MESSAGE = "Not enough stock to buy computer with id %s";
    private static final String COMPUTER_NOT_FOUND_BRAND_VERSION_MESSAGE = "Computer with brand %s and version %s was not found";
    private static final String SALE_NOT_FOUND_MESSAGE = "Sale with id %s was not found";
    private static final int MAX_CLIENT_NAME_LENGTH = 255;
    private static final int MAX_SALESMAN_LENGTH = 50;

    @Autowired
    private ComputerRepository computerRepository;
    @Autowired
    private SaleRepository saleRepository;

    protected void validate(SaleDto saleDto) {
        String clientName = saleDto.getClientName();
        if (StringUtils.isNotBlank(clientName) && clientName.length() >= MAX_CLIENT_NAME_LENGTH)
            throw new ValidationException(CLIENT_NAME_SIZE_MESSAGE);
        String salesman = saleDto.getSalesman();
        if (StringUtils.isNotBlank(salesman) && salesman.length() >= MAX_SALESMAN_LENGTH)
            throw new ValidationException(SALESMAN_SIZE_MESSAGE);
        if (saleDto.getQuantity() <= 0)
            throw new ValidationException(NEGATIVE_QUANTITY_MESSAGE);
        if (StringUtils.isBlank(saleDto.getSaleDate()))
            throw new ValidationException(EMPTY_SALE_DATE_MESSAGE);
        try {
            Utils.parseDate(saleDto.getSaleDate());
        } catch (DateParsingException parseException) {
            throw new ValidationException(WRONG_FORMAT_SALE_DATE_MESSAGE.formatted(saleDto.getSaleDate()), parseException);
        }
    }

    public SaleComputerDto validatePost(SaleDto saleDto) {
        if (saleDto.getId() != 0)
            throw new ValidationException(POST_ID_SET_MESSAGE);
        validate(saleDto);
        var saleComputerDto = initSaleComputerDto(saleDto);
        var computer = saleComputerDto.getComputer();
        var computerStore = computer.getComputerStore();
        if (computerStore.getStock() < 1)
            throw new EmptyStoreException(NO_STOCK_MESSAGE.formatted(computer.getId()));
        return saleComputerDto;
    }

    private SaleComputerDto initSaleComputerDto(SaleDto saleDto) {
        var saleComputerDto = new SaleComputerDto();
        saleComputerDto.setComputer(computerRepository.findByBrandAndVersion(saleDto.getComputerBrand(), saleDto.getComputerVersion())
                .orElseThrow(() -> new ComputerNotFoundException(COMPUTER_NOT_FOUND_BRAND_VERSION_MESSAGE.formatted(saleDto.getComputerBrand(), saleDto.getComputerVersion()))));
        return saleComputerDto;
    }

    public SaleComputerDto validatePut(SaleDto saleDto) {
        // Do not update stock for modification of a sale
        if (saleDto.getId() == 0)
            throw new ValidationException(PUT_ID_NOT_SET_MESSAGE);
        validate(saleDto);
        var saleComputerDto = initSaleComputerDto(saleDto);
        saleComputerDto.setSale(saleRepository.findById(saleDto.getId())
                .orElseThrow(() -> new SaleNotFoundException(SALE_NOT_FOUND_MESSAGE.formatted(saleDto.getId()))));
        return saleComputerDto;
    }

    /**
     * Return true if object exists
     *
     * @param saleId the id of the sale
     * @return
     */
    public boolean validateDelete(int saleId) {
        return saleRepository.findById(saleId).isPresent();
    }
}
