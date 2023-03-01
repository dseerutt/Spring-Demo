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

    @Autowired
    private ComputerRepository computerRepository;

    protected void validate(LightComputerDto lightComputerDto) {
        String description = lightComputerDto.getDescription();
        if (StringUtils.isNotBlank(description) && description.length() >= 255)
            throw new ValidationException("Description is limited to 255 characters");
        lightComputerDto.setPrice(Math.round(lightComputerDto.getPrice() * 100) / 100.);
    }

    public void validatePost(LightComputerDto lightComputerDto) {
        if (lightComputerDto.getId() != 0)
            throw new ValidationException("Id cannot be set when using POST");
        if (computerRepository.existsByBrandAndVersion(lightComputerDto.getBrand(), lightComputerDto.getVersion())) {
            throw new ComputerAlreadyExistsException("Computer with brand " + lightComputerDto.getBrand() + " and version " + lightComputerDto.getVersion() + " already exists");
        }
        validate(lightComputerDto);
    }

    public Computer validatePut(LightComputerDto lightComputerDto) {
        if (lightComputerDto.getId() == 0)
            throw new ValidationException("Computer Id has to be related to an existing computer when using POST");
        validate(lightComputerDto);

        return computerRepository.findById(lightComputerDto.getId())
                .orElseThrow(() -> new ComputerNotFoundException("Computer with id " + lightComputerDto.getId() + " was not found"));
    }
}
