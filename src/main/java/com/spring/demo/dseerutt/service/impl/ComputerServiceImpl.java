package com.spring.demo.dseerutt.service.impl;

import com.spring.demo.dseerutt.dto.item.ComputerDto;
import com.spring.demo.dseerutt.dto.mapper.ComputerDtoMapper;
import com.spring.demo.dseerutt.model.exception.computer.ComputerNotFoundException;
import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.repository.ComputerRepository;
import com.spring.demo.dseerutt.service.ComputerService;
import com.spring.demo.dseerutt.service.validator.ComputerDtoValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ComputerServiceImpl implements ComputerService {
    private static final Logger LOGGER = LogManager.getLogger(ComputerServiceImpl.class);
    private ComputerDtoMapper mapper = Mappers.getMapper(ComputerDtoMapper.class);

    @Autowired
    private ComputerRepository computerRepository;
    @Autowired
    ComputerDtoValidator computerDtoValidator;

    @Override
    public ComputerDto getComputer(int id) {
        return mapper.computerToComputerDto(computerRepository.findById(id).orElseThrow(() -> {
            String message = "Computer not found with id " + id;
            LOGGER.error(message);
            return new ComputerNotFoundException(message);
        }));
    }

    @Override
    public List<ComputerDto> getAllComputers() {
        return computerRepository.findAll().stream().map(computer -> mapper.computerToComputerDto(computer)).collect(Collectors.toList());
    }

    @Override
    public ComputerDto addComputer(ComputerDto computerDto) {
        computerDtoValidator.validatePost(computerDto);
        Computer computer = mapper.computerDtoToComputer(computerDto);
        computer.setSerialNumber(UUID.randomUUID().toString());
        return mapper.computerToComputerDto(computerRepository.save(computer));
    }

    @Override
    public ComputerDto updateComputer(ComputerDto computerDto) {
        Computer element = computerDtoValidator.validatePut(computerDto);
        element.setBrand(computerDto.getBrand());
        element.setDescription(computerDto.getDescription());
        element.setVersion(computerDto.getVersion());
        element.setPrice(computerDto.getPrice());
        Computer savedElement = computerRepository.save(element);
        return mapper.computerToComputerDto(savedElement);
    }

    @Override
    public void deleteComputer(int id) {
        computerRepository.deleteById(id);
    }
}
