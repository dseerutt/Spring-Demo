package com.spring.demo.dseerutt.service;

import com.spring.demo.dseerutt.model.object.Computer;
import com.spring.demo.dseerutt.repository.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComputerServiceImpl implements ComputerService {

    @Autowired
    private ComputerRepository computerRepository;

    @Override
    public Computer findComputer(String id) {
        return computerRepository.findById(id).orElse(null);
    }
}
