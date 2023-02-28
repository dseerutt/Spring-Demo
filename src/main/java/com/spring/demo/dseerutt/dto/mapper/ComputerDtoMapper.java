package com.spring.demo.dseerutt.dto.mapper;

import com.spring.demo.dseerutt.dto.item.client.ComputerDto;
import com.spring.demo.dseerutt.dto.item.client.LightComputerDto;
import com.spring.demo.dseerutt.model.object.Computer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ComputerDtoMapper {

    @Mapping(target = "computerStore", ignore = true)
    Computer computerDtoToComputer(LightComputerDto lightComputerDto);

    @Mapping(source = "computer.computerStore.lastProvisionDate", target = "lastProvisionDate", dateFormat = "dd-MM-yyyy")
    @Mapping(source = "computer.computerStore.stock", target = "stock")
    ComputerDto computerToComputerDto(Computer computer);
}
