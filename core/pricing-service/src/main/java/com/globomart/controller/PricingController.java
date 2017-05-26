package com.globomart.controller;

import com.globomart.controller.exception.RecordNotFoundException;
import com.globomart.service.PricingService;
import com.globomart.service.exception.RecordDoesNotExistException;
import com.globomart.service.exception.ServiceException;
import com.globomart.vo.PriceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(value="pricing-service", description="Operations pertaining to pricing of the products")
@RequestMapping(value = "product-prices", produces = MediaType.APPLICATION_JSON_VALUE)
public class PricingController {

    private PricingService service;

    @Autowired
    public PricingController(PricingService service) {
        this.service = service;
    }

    @ApiOperation(value = "Gets the price of the product by name and type", response = PriceVO.class)
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
