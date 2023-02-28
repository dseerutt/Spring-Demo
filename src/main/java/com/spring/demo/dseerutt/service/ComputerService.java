package com.spring.demo.dseerutt.service;

import com.spring.demo.dseerutt.dto.item.client.ComputerDto;
import com.spring.demo.dseerutt.dto.item.client.ComputerStatusDto;
import com.spring.demo.dseerutt.dto.item.client.LightComputerDto;
import com.spring.demo.dseerutt.dto.item.client.ProvisionDto;

import java.util.List;

public interface ComputerService {

    ComputerDto getComputer(int id);

    List<ComputerDto> getAllComputers();

    ComputerDto addComputer(LightComputerDto lightComputerDto);

    ComputerDto updateComputer(LightComputerDto lightComputerDto);

    ProvisionDto provisionComputer(ProvisionDto computer);

    ProvisionDto deprovisionComputer(ProvisionDto computer);

    void updateComputerStatus(ComputerStatusDto computerStatusDto, int id);
}
