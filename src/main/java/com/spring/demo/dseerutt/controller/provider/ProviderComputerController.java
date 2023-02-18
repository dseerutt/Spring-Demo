package com.spring.demo.dseerutt.controller.provider;

import com.spring.demo.dseerutt.dto.item.client.ComputerDto;
import com.spring.demo.dseerutt.dto.item.client.ProvisionDto;
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

@RestController
@RequestMapping(value = {"/provider/computer"})
public class ProviderComputerController {
    private static final Logger LOGGER = LogManager.getLogger(ProviderComputerController.class);

    @Autowired
    private ComputerService computerService;

    @PostMapping(value = StringUtils.EMPTY, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ComputerDto> addComputer(@RequestBody @NonNull ComputerDto computerDto) {
        LOGGER.info("Provider Computer POST WS was called");
        return new ResponseEntity<>(computerService.addComputer(computerDto), HttpStatus.CREATED);
    }

    @PutMapping(value = StringUtils.EMPTY, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ComputerDto> updateComputer(@RequestBody @NonNull ComputerDto computerDto) {
        LOGGER.info("Provider Computer PUT WS was called");
        return new ResponseEntity<>(computerService.updateComputer(computerDto), HttpStatus.OK);
    }

    @PostMapping(value = "/provision", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ProvisionDto> provisionComputer(@RequestBody @NonNull ProvisionDto provisionDto) {
        LOGGER.info("Provider Computer provision POST WS was called");
        return new ResponseEntity<>(computerService.provisionComputer(provisionDto), HttpStatus.OK);
    }

    @PostMapping(value = "/deprovision", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ProvisionDto> deprovisionComputer(@RequestBody @NonNull ProvisionDto provisionDto) {
        LOGGER.info("Provider Computer deprovision POST WS was called");
        return new ResponseEntity<>(computerService.deprovisionComputer(provisionDto), HttpStatus.OK);
    }

    //Computer deletion is not available
}
