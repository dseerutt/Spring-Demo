package com.spring.demo.dseerutt.dto.mapper;

import com.spring.demo.dseerutt.dto.item.ComputerDto;
import com.spring.demo.dseerutt.model.object.Computer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ComputerDtoMapper {

    @Mapping(target = "computerStore", ignore = true)
    Computer computerDtoToComputer(ComputerDto computerDto);

    @Mapping(source = "computer.computerStore.stock", target = "stock")
    ComputerDto computerToComputerDto(Computer computer);
}
