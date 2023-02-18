package com.spring.demo.dseerutt.service.validator;

import com.spring.demo.dseerutt.dto.item.client.ComputerDto;
import com.spring.demo.dseerutt.model.exception.computer.ComputerAlreadyExistsException;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.exception.server.ValidationException;
import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.repository.ComputerRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComputerDtoValidator {
    private static final Logger LOGGER = LogManager.getLogger(ComputerDtoValidator.class);

    @Autowired
    private ComputerRepository computerRepository;

    public void validate(ComputerDto computerDto) {
        String description = computerDto.getDescription();
        if (StringUtils.isNotBlank(description) && description.length() >= 255) {
            throw new ValidationException("Description is limited to 255 characters");
        }
        computerDto.setPrice(Math.round(computerDto.getPrice() * 100) / 100);
        if (computerDto.getStock() != 0) {
            throw new ValidationException("Stock cannot be updated using this WS");
        }
        if (computerDto.getLastProvisionDate() != null) {
            throw new ValidationException("Last Provision Date cannot be updated");
        }
    }

    public void validatePost(ComputerDto computerDto) {
        if (computerDto.getId() != 0) {
            throw new ValidationException("Id cannot be set when using POST");
        }
        if (computerRepository.existsByBrandAndVersion(computerDto.getBrand(), computerDto.getVersion())) {
            String message = "Computer with brand " + computerDto.getBrand() + " and version " + computerDto.getVersion() + " already exists";
            LOGGER.error(message);
            throw new ComputerAlreadyExistsException(message);
        }
        validate(computerDto);
    }

    public Computer validatePut(ComputerDto computerDto) {
        if (computerDto.getId() == 0) {
            throw new ValidationException("Computer Id has to be related to an existing computer when using POST");
        }
        validate(computerDto);

        return computerRepository.findById(computerDto.getId())
                .orElseThrow(() -> {
                    String message = "Computer with id " + computerDto.getId() + " was not found";
                    LOGGER.error(message);
                    return new ComputerNotFoundException(message);
                });
    }
}
