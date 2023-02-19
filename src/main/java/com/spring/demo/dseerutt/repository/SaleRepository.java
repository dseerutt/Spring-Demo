package com.spring.demo.dseerutt.repository;

import com.spring.demo.dseerutt.model.object.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface SaleRepository extends PagingAndSortingRepository<Sale, Integer> {

    Optional<Sale> findById(int id);

    Page<Sale> findAll(Pageable pageable);

    void deleteById(int id);

    Sale save(Sale sale);
}
