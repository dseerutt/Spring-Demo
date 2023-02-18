package com.spring.demo.dseerutt.repository;

import com.spring.demo.dseerutt.model.object.Computer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComputerRepository extends JpaRepository<Computer, String> {

    Optional<Computer> findById(int id);

    Optional<Computer> findByBrandAndVersion(String brand, String version);

    List<Computer> findAll();

    boolean existsByBrandAndVersion(String brand, String version);

    void deleteById(int id);
}
