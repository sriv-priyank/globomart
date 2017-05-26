package com.globomart.controller;

import com.globomart.controller.exception.RecordNotFoundException;
import com.globomart.service.PricingService;
import com.globomart.service.exception.RecordDoesNotExistException;
import com.globomart.service.exception.ServiceException;
import com.globomart.vo.PriceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "product-prices", produces = MediaType.APPLICATION_JSON_VALUE)
public class PricingController {

    private PricingService service;

    @Autowired
    public PricingController(PricingService service) {
        this.service = service;
    }

    @GetMapping(value = "/name/{name}/type/{type}")
    public PriceVO getProductPrice(@PathVariable final String name,
                                   @PathVariable final String type)
    {
        try {
            return service.getProductPrice(name, type);

        } catch (RecordDoesNotExistException ex) {
            throw new RecordNotFoundException(ex);
        }
    }
}
