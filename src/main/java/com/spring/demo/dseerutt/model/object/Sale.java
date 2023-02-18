package com.spring.demo.dseerutt.model.object;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

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
    private Date saleDate;

    @Column(name = "salesman")
    private String salesman;
}
