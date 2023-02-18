package com.spring.demo.dseerutt.controller.provider;

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
@RequestMapping(value = {"/provider/sale"})
public class ProviderSaleController {
    private static final Logger LOGGER = LogManager.getLogger(ProviderSaleController.class);

    @Autowired
    private SaleService saleService;

    @PutMapping(value = StringUtils.EMPTY, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<SaleDto> updateSale(@RequestBody @NonNull SaleDto saleDto) {
        LOGGER.info("Provider Sale PUT WS was called");
        return new ResponseEntity<>(saleService.updateSale(saleDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{saleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> removeSale(@PathVariable @NonNull int saleId) {
        LOGGER.info("Provider Sale DELETE WS was called");
        saleService.deleteSale(saleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
