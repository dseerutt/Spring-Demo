package com.spring.demo.dseerutt.dto.item.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class ProvisionDto {

    private int id;
    private String brand;
    private String version;
    private int quantity;
}
