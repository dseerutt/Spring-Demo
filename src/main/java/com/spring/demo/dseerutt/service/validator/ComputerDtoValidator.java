package com.spring.demo.dseerutt.service.validator;

import com.spring.demo.dseerutt.dto.item.client.LightComputerDto;
import com.spring.demo.dseerutt.model.exception.computer.ComputerAlreadyExistsException;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.exception.server.ValidationException;
import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.repository.ComputerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComputerDtoValidator {
    private static final String DESCRIPTION_TOO_LONG_MESSAGE = "Description is limited to 255 characters";
    private static final String POST_ID_ALREADY_SET_MESSAGE = "Id cannot be set when using POST";
    private static final String PUT_ID_NOT_SET_MESSAGE = "Computer Id has to be related to an existing computer when using POST";
    private static final String COMPUTER_ALREADY_EXISTS_BRAND_VERSION_MESSAGE = "Computer with brand %s and version %s already exists";
    private static final String COMPUTER_ALREADY_EXISTS_ID_MESSAGE = "Computer with id %s was not found";
    private static final int MAX_DESCRIPTION_LENGTH = 255;

    @Autowired
    private ComputerRepository computerRepository;

    protected void validate(LightComputerDto lightComputerDto) {
        String description = lightComputerDto.getDescription();
        if (StringUtils.isNotBlank(description) && description.length() >= MAX_DESCRIPTION_LENGTH)
            throw new ValidationException(DESCRIPTION_TOO_LONG_MESSAGE);
        lightComputerDto.setPrice(Math.round(lightComputerDto.getPrice() * 100) / 100.);
    }

    public void validatePost(LightComputerDto lightComputerDto) {
        if (lightComputerDto.getId() != 0)
            throw new ValidationException(POST_ID_ALREADY_SET_MESSAGE);
        if (computerRepository.existsByBrandAndVersion(lightComputerDto.getBrand(), lightComputerDto.getVersion())) {
            throw new ComputerAlreadyExistsException(COMPUTER_ALREADY_EXISTS_BRAND_VERSION_MESSAGE.formatted(lightComputerDto.getBrand(), lightComputerDto.getVersion()));
        }
        validate(lightComputerDto);
    }

    public Computer validatePut(LightComputerDto lightComputerDto) {
        if (lightComputerDto.getId() == 0)
            throw new ValidationException(PUT_ID_NOT_SET_MESSAGE);
        validate(lightComputerDto);

        return computerRepository.findById(lightComputerDto.getId())
                .orElseThrow(() -> new ComputerNotFoundException(COMPUTER_ALREADY_EXISTS_ID_MESSAGE.formatted(lightComputerDto.getId())));
    }
}
