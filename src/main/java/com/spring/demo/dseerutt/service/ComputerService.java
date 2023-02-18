package com.spring.demo.dseerutt.service;

import com.spring.demo.dseerutt.dto.item.ComputerDto;

import java.util.List;

public interface ComputerService {

    ComputerDto getComputer(int id);

    List<ComputerDto> getAllComputers();

    ComputerDto addComputer(ComputerDto computer);

    ComputerDto updateComputer(ComputerDto computer);

    void deleteComputer(int id);
}
