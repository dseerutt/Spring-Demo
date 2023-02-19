package com.spring.demo.dseerutt.service;

import com.spring.demo.dseerutt.dto.item.client.ComputerDto;
import com.spring.demo.dseerutt.dto.item.client.ProvisionDto;

import java.util.List;

public interface ComputerService {

    ComputerDto getComputer(int id);

    List<ComputerDto> getAllComputers();

    ComputerDto addComputer(ComputerDto computer);

    ComputerDto updateComputer(ComputerDto computer);

    ProvisionDto provisionComputer(ProvisionDto computer);

    ProvisionDto deprovisionComputer(ProvisionDto computer);
}
