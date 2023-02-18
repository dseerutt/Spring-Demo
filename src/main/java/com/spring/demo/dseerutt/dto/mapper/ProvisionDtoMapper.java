package com.spring.demo.dseerutt.dto.mapper;

import com.spring.demo.dseerutt.dto.item.client.ProvisionDto;
import com.spring.demo.dseerutt.model.object.Computer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProvisionDtoMapper {

    @Mapping(source = "computer.computerStore.stock", target = "quantity")
    ProvisionDto computerToProvisionDto(Computer computer);
}
