package com.globomart.controller;

import com.globomart.controller.exception.RecordNotFoundException;
import com.globomart.service.ProductService;
import com.globomart.service.exception.RecordDoesNotExistException;
import com.globomart.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductVO add(@RequestBody final ProductVO product)
    {
        return service.add(product);
    }

    @GetMapping
    public List<ProductVO> getProducts()
    {
        try {
            return service.getProducts();

        } catch(RecordDoesNotExistException ex) {
            throw new RecordNotFoundException(ex);
        }
    }

    @GetMapping("/type/{type}")
    public List<ProductVO> getProductsByType(@PathVariable final String type)
    {
        try {
            return service.getProductsByType(type);

        } catch(RecordDoesNotExistException ex) {
            throw new RecordNotFoundException(ex);
        }
    }

    @GetMapping("/name/{name}/type/{type}")
    public List<ProductVO> getProductsByNameAndType(@PathVariable final String name,
                                                    @PathVariable final String type)
    {
        try {
            return service.getProductsByNameAndType(name, type);

        } catch(RecordDoesNotExistException ex) {
            throw new RecordNotFoundException(ex);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id)
    {
        try {
            service.delete(id);

        } catch(RecordDoesNotExistException ex) {
            throw new RecordNotFoundException(ex);
        }
    }
}
