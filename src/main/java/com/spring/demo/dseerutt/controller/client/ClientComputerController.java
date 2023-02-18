package com.spring.demo.dseerutt.controller.client;

import com.spring.demo.dseerutt.dto.item.client.ComputerDto;
import com.spring.demo.dseerutt.service.ComputerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/client/computer"})
public class ClientComputerController {
    private static final Logger LOGGER = LogManager.getLogger(ClientComputerController.class);

    @Autowired
    private ComputerService computerService;

    @GetMapping(value = "/{computerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ComputerDto> getComputer(@PathVariable @NonNull int computerId) {
        LOGGER.info("Client Computer GET WS was called");
        ComputerDto computerDto = computerService.getComputer(computerId);
        return new ResponseEntity<>(computerDto, HttpStatus.OK);
    }

    @GetMapping(value = StringUtils.EMPTY, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<ComputerDto>> getAllComputers() {
        LOGGER.info("Client Computers GET WS was called");
        return new ResponseEntity<>(computerService.getAllComputers(), HttpStatus.OK);
    }
}
