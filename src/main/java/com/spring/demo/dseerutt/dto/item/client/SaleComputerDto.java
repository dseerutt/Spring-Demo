package com.spring.demo.dseerutt.dto.item.client;

import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.model.object.Sale;
import lombok.*;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Setter
@Getter
public class SaleComputerDto {

    private Sale sale;
    private Computer computer;
}
