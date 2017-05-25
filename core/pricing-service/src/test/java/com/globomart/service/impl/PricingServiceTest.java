package com.globomart.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globomart.domain.entity.Price;
import com.globomart.domain.repository.PriceRepository;
import com.globomart.mapper.Mapper;
import com.globomart.mapper.MapperConfig;
import com.globomart.service.PricingService;
import com.globomart.service.exception.RecordDoesNotExistException;
import com.globomart.vo.PriceVO;
import com.globomart.vo.ProductVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class PricingServiceTest {

    @Mock
    private PriceRepository repository;
    private PricingService service;
    private ProductVO productVO;
    private Price price;

    @InjectMocks
    private MapperConfig mapperConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new PricingServiceImpl(repository, null, null, mapperConfig.mapperComponent());
        productVO = product(1L, "Corsair RAM Value Series - 4GB DDR4 2133MHz", "ram");
        price = price(2L, 1L, 2315.0d);
    }

    @Test
    public void testGetPrice() {
        Mockito.doReturn(price).when(repository).findByProductId(Mockito.anyLong());
        PriceVO resp = service.getPriceByProduct(productVO.getId());
        Assert.assertTrue(price.getPrice().equals(resp.getPrice()));
    }

    @Test(expected = RecordDoesNotExistException.class)
    public void testGetPriceNotFound() {
        Mockito.doThrow(RecordDoesNotExistException.class).when(repository).findByProductId(Mockito.anyLong());
        service.getPriceByProduct(Long.MAX_VALUE);
    }

    private ProductVO product(Long id, String name, String type) {
        ProductVO product = new ProductVO();
        product.setId(id);
        product.setName(name);
        product.setType(type);
        return product;
    }

    private Price price(Long id, Long productId, Double price) {
        Price entity = new Price();
        entity.setId(id);
        entity.setProductId(productId);
        entity.setPrice(price);
        return entity;
    }
}
