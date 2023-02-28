package com.spring.demo.dseerutt.dto.item.client;

import lombok.*;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Setter
@Getter
public class ComputerDto {

    private int id;
    private String brand;
    private String version;
    private String description;
    private double price;
    private String lastProvisionDate;
    private int stock;
}
