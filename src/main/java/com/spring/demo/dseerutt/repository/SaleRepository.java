package com.spring.demo.dseerutt.repository;

import com.spring.demo.dseerutt.model.object.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, String> {

    Optional<Sale> findById(int id);

    List<Sale> findAll();

    void deleteById(int id);
}
