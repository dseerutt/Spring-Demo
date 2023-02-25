package com.spring.demo.dseerutt.repository;

import com.spring.demo.dseerutt.model.object.Computer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ComputerRepository extends JpaRepository<Computer, Integer> {

    Optional<Computer> findById(int id);

    Optional<Computer> findByBrandAndVersion(String brand, String version);

    List<Computer> findAll();

    boolean existsByBrandAndVersion(String brand, String version);

    void deleteById(int id);

    @Transactional
    @Modifying
    @Query("UPDATE ComputerStore cs SET cs.enabled = :status WHERE cs.id = :id")
    void updateComputerStatus(int id, boolean status);
}
