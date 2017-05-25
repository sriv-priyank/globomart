package com.globomart.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globomart.domain.entity.Product;
import com.globomart.domain.repository.ProductRepository;
import com.globomart.mapper.Mapper;
import com.globomart.mapper.MapperConfig;
import com.globomart.service.ProductService;
import com.globomart.vo.ProductVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class ProductServiceTest {

    @Mock
    private ProductRepository repository;
    private ProductService service;
    private List<Product> products;

    @InjectMocks
    private MapperConfig mapperConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new ProductServiceImpl(repository, mapperConfig.mapperComponent());

        products = Arrays.asList(
                product(1L, "Gigabyte GA-B250m-DS3H Motherboard", "motherboard"),
                product(2L, "Intel Core i5-7500 Processor", "processor"),
                product(3L, "Kingston RAM HyperX Fury Series - 8GB DDR4 2133MHz", "ram"),
                product(4L, "Corsair RAM Value Series - 4GB DDR4 2133MHz", "ram")
        );
    }

    @Test
    public void testGetProducts() {
        Mockito.doReturn(products).when(repository).findAll();
        List<ProductVO> productVos = service.getProducts();
        Assert.assertTrue(productVos.size() == products.size());
    }

    @Test
    public void testGetProductsByNameAndType() {
        Mockito.doReturn(products).when(repository).findByNameAndType(
                Mockito.anyString(), Mockito.anyString());

        List<ProductVO> productVos = service.getProductsByNameAndType("Intel Core i5-7500 Processor", "processor");
        Assert.assertTrue(productVos.size() == products.size());
    }

    @Test
    public void testGetProductsByType() {
        Mockito.doReturn(products).when(repository).findByType(Mockito.anyString());

        List<ProductVO> productVos = service.getProductsByType("ram");
        Assert.assertTrue(productVos.size() == products.size());
    }

    @Test
    public void testDeleteProduct() {
        Product p = products.get(0);
        Mockito.when(repository.findOne(p.getId())).thenReturn(p);
        service.delete(p.getId());
        Mockito.verify(repository).delete(p);
    }


    private Product product(Long id, String name, String type) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setType(type);
        return product;
    }
}
