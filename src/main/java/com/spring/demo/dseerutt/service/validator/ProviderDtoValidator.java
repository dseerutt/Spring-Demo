package com.spring.demo.dseerutt.service.validator;

import com.spring.demo.dseerutt.dto.item.client.ProvisionDto;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.exception.computer.EmptyStoreException;
import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.repository.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderDtoValidator {
    private static final String COMPUTER_NOT_FOUND_ID_MESSAGE = "Computer with id %s was not found";
    private static final String COMPUTER_NOT_FOUND_BRAND_VERSION_MESSAGE = "Computer with brand %s and version %s was not found";
    private static final String EMPTY_STORE_MESSAGE = "Cannot deprovision computer %s, there is not enough stock";

    @Autowired
    private ComputerRepository computerRepository;

    public Computer validate(ProvisionDto provisionDto) {
        if (provisionDto.getId() != 0) {
            return computerRepository.findById(provisionDto.getId())
                    .orElseThrow(() -> new ComputerNotFoundException(COMPUTER_NOT_FOUND_ID_MESSAGE.formatted(provisionDto.getId())));
        } else {
            return computerRepository.findByBrandAndVersion(provisionDto.getBrand(), provisionDto.getVersion())
                    .orElseThrow(() -> new ComputerNotFoundException(COMPUTER_NOT_FOUND_BRAND_VERSION_MESSAGE.formatted(provisionDto.getBrand(), provisionDto.getVersion())));
        }
    }

    public Computer validateProvision(ProvisionDto provisionDto) {
        return validate(provisionDto);
    }

    public Computer validateDeprovision(ProvisionDto provisionDto) {
        var computer = validate(provisionDto);
        if (computer.getComputerStore().getStock() < provisionDto.getQuantity())
            throw new EmptyStoreException(EMPTY_STORE_MESSAGE.formatted(computer.getId()));
        return computer;
    }
}
