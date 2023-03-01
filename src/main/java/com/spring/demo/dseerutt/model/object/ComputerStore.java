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
@Table(name = "computerstore")
public class ComputerStore {

    @Id
    @Column(name = "computer_id")
    private int id;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "computer_id", referencedColumnName = "id")
    private Computer computer;

    @Column(name = "lastProvisionDate")
    private LocalDate lastProvisionDate;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "stock")
    private int stock;
}
