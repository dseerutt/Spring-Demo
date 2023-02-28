package com.spring.demo.dseerutt.service.validator;

import com.spring.demo.dseerutt.dto.item.client.ProvisionDto;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.exception.computer.EmptyStoreException;
import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.repository.ComputerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderDtoValidator {
    private static final Logger LOGGER = LogManager.getLogger(ProviderDtoValidator.class);

    @Autowired
    private ComputerRepository computerRepository;

    public Computer validate(ProvisionDto provisionDto) {
        if (provisionDto.getId() != 0) {
            return computerRepository.findById(provisionDto.getId())
                    .orElseThrow(() -> {
                        String message = "Computer with id " + provisionDto.getId() + " was not found";
                        LOGGER.error(message);
                        return new ComputerNotFoundException(message);
                    });
        } else {
            return computerRepository.findByBrandAndVersion(provisionDto.getBrand(), provisionDto.getVersion())
                    .orElseThrow(() -> {
                        String message = "Computer with brand " + provisionDto.getBrand() + " and version " + provisionDto.getVersion() + " was not found";
                        LOGGER.error(message);
                        return new ComputerNotFoundException(message);
                    });
        }
    }

    public Computer validateProvision(ProvisionDto provisionDto) {
        return validate(provisionDto);
    }

    public Computer validateDeprovision(ProvisionDto provisionDto) {
        var computer = validate(provisionDto);
        if (computer.getComputerStore().getStock() < provisionDto.getQuantity())
            throw new EmptyStoreException("Cannot deprovision computer " + computer.getId() + ", there is not enough stock");
        return computer;
    }
}
