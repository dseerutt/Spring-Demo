package com.spring.demo.dseerutt.model.object;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "computer")
public class Computer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(mappedBy = "computer")
    private ComputerStore computerStore;

    @Column(name = "brand")
    private String brand;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "version")
    private String version;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;
}
