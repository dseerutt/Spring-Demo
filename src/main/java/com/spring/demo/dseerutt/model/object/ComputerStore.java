package com.spring.demo.dseerutt.model.object;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;


@ToString
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "computerstore")
public class ComputerStore {

    @Id
    @Column(name = "computer_id")
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "computer_id")
    private Computer computer;

    @Column(name = "lastProvisionDate")
    private Date lastProvisionDate;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "stock")
    private int stock;
}