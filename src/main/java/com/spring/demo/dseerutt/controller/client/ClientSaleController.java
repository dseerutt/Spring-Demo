package com.spring.demo.dseerutt.controller.client;

import com.spring.demo.dseerutt.dto.item.client.SaleDto;
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

@RestController
@RequestMapping(value = {"/client/sale"})
public class ClientSaleController {
    private static final Logger LOGGER = LogManager.getLogger(ClientSaleController.class);

    @Autowired
    private SaleService saleService;

    @PostMapping(value = StringUtils.EMPTY, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<SaleDto> addSale(@RequestBody @NonNull SaleDto saleDto) {
        LOGGER.info("Sale POST WS was called");
        return new ResponseEntity<>(saleService.addSale(saleDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{saleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<SaleDto> getSale(@PathVariable @NonNull int saleId) {
        LOGGER.info("Client Sale GET WS was called");
        return new ResponseEntity<>(saleService.getSale(saleId), HttpStatus.OK);
    }
}
