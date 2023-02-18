package com.spring.demo.dseerutt.controller;

import com.spring.demo.dseerutt.dto.item.SaleDto;
import com.spring.demo.dseerutt.service.SaleService;
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
@RequestMapping(value = {"/sale"})
public class SaleController {
    private static final Logger LOGGER = LogManager.getLogger(SaleController.class);

    @Autowired
    private SaleService saleService;

    @PostMapping(value = StringUtils.EMPTY, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<SaleDto> addSale(@RequestBody @NonNull SaleDto saleDto) {
        LOGGER.info("Sale POST WS was called");
        return new ResponseEntity<>(saleService.addSale(saleDto), HttpStatus.CREATED);
    }

    @GetMapping(value = StringUtils.EMPTY, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<SaleDto>> getAllSales() {
        LOGGER.info("Sale GET WS was called");
        return new ResponseEntity<>(saleService.getAllSales(), HttpStatus.OK);
    }

    @GetMapping(value = "/{saleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<SaleDto> getSale(@PathVariable @NonNull int saleId) {
        LOGGER.info("Sale GET WS was called");
        return new ResponseEntity<>(saleService.getSale(saleId), HttpStatus.OK);
    }

    @PutMapping(value = StringUtils.EMPTY, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<SaleDto> updateSale(@RequestBody @NonNull SaleDto saleDto) {
        LOGGER.info("Sale PUT WS was called");
        return new ResponseEntity<>(saleService.updateSale(saleDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{saleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> removeSale(@PathVariable @NonNull int saleId) {
        LOGGER.info("Sale DELETE WS was called");
        saleService.deleteSale(saleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
