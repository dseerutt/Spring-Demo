package com.spring.demo.dseerutt.model.object;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "client_name")
    private String clientName;

    @ManyToOne
    private Computer computer;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "sale_date")
    private LocalDate saleDate;

    @Column(name = "salesman")
    private String salesman;
}
