package com.spring.demo.dseerutt.dto.item.client;

import lombok.*;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Setter
@Getter
public class SaleDto {

    private int id;
    private String clientName;
    private String salesman;
    private String computerBrand;
    private String computerVersion;
    private int quantity;
    private String saleDate;

}
