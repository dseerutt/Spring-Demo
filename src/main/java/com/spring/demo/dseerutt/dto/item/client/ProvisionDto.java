package com.spring.demo.dseerutt.dto.item.client;

import lombok.*;

@EqualsAndHashCode
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
