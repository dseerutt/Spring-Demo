package com.spring.demo.dseerutt.service.impl;

import com.spring.demo.dseerutt.dto.item.client.ComputerDto;
import com.spring.demo.dseerutt.dto.item.client.ComputerStatusDto;
import com.spring.demo.dseerutt.dto.item.client.LightComputerDto;
import com.spring.demo.dseerutt.dto.item.client.ProvisionDto;
import com.spring.demo.dseerutt.dto.mapper.ComputerDtoMapper;
import com.spring.demo.dseerutt.dto.mapper.ProvisionDtoMapper;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.repository.ComputerRepository;
import com.spring.demo.dseerutt.service.ComputerService;
import com.spring.demo.dseerutt.service.validator.ComputerDtoValidator;
import com.spring.demo.dseerutt.service.validator.ProviderDtoValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ComputerServiceImpl implements ComputerService {
    private static final Logger LOGGER = LogManager.getLogger(ComputerServiceImpl.class);
    private final ComputerDtoMapper computerMapper = Mappers.getMapper(ComputerDtoMapper.class);
    private final ProvisionDtoMapper provisionMapper = Mappers.getMapper(ProvisionDtoMapper.class);

    @Autowired
    private ComputerRepository computerRepository;
    @Autowired
    ComputerDtoValidator computerDtoValidator;
    @Autowired
    ProviderDtoValidator providerDtoValidator;

    @Override
    public ComputerDto getComputer(int id) {
        return computerMapper.computerToComputerDto(computerRepository.findById(id).orElseThrow(() -> {
            String message = "Computer not found with id " + id;
            LOGGER.error(message);
            return new ComputerNotFoundException(message);
        }));
    }

    @Override
    public List<ComputerDto> getAllComputers() {
        return computerRepository.findAll().stream()
                .map(computerMapper::computerToComputerDto)
                .collect(Collectors.toList());
    }

    @Override
    public ComputerDto addComputer(LightComputerDto lightComputerDto) {
        computerDtoValidator.validatePost(lightComputerDto);
        var computer = computerMapper.computerDtoToComputer(lightComputerDto);
        computer.setSerialNumber(UUID.randomUUID().toString());
        return computerMapper.computerToComputerDto(computerRepository.save(computer));
    }

    @Override
    public ComputerDto updateComputer(LightComputerDto lightComputerDto) {
        var element = computerDtoValidator.validatePut(lightComputerDto);
        element.setBrand(lightComputerDto.getBrand());
        element.setDescription(lightComputerDto.getDescription());
        element.setVersion(lightComputerDto.getVersion());
        element.setPrice(lightComputerDto.getPrice());
        var savedElement = computerRepository.save(element);
        return computerMapper.computerToComputerDto(savedElement);
    }

    @Override
    public ProvisionDto provisionComputer(ProvisionDto provisionDto) {
        var computer = providerDtoValidator.validateProvision(provisionDto);
        var computerStore = computer.getComputerStore();
        computerStore.setStock(computerStore.getStock() + provisionDto.getQuantity());
        computerStore.setLastProvisionDate(new Date(System.currentTimeMillis()));
        Computer savedElement = computerRepository.save(computer);
        return provisionMapper.computerToProvisionDto(savedElement);
    }

    @Override
    public ProvisionDto deprovisionComputer(ProvisionDto provisionDto) {
        var computer = providerDtoValidator.validateDeprovision(provisionDto);
        var computerStore = computer.getComputerStore();
        computerStore.setStock(computerStore.getStock() - provisionDto.getQuantity());
        computerStore.setLastProvisionDate(new Date(System.currentTimeMillis()));
        var savedElement = computerRepository.save(computer);
        return provisionMapper.computerToProvisionDto(savedElement);
    }

    @Override
    public void updateComputerStatus(ComputerStatusDto computerStatusDto, int id) {
        computerRepository.updateComputerStatus(id, computerStatusDto.isEnabled());
    }
}
