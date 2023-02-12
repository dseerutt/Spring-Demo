package com.spring.demo.dseerutt.repository;

import com.spring.demo.dseerutt.model.object.Computer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComputerRepository extends JpaRepository<Computer, String> {

    Optional<Computer> findById(String id);
}
