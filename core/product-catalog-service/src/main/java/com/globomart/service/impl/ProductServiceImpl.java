package com.globomart.service.impl;

import com.globomart.domain.entity.Product;
import com.globomart.domain.repository.ProductRepository;
import com.globomart.mapper.Mapper;
import com.globomart.service.ProductService;
import com.globomart.service.exception.RecordDoesNotExistException;
import com.globomart.vo.ProductVO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
final class ProductServiceImpl implements ProductService {

    private ProductRepository repository;
    private Mapper mapper;

    public ProductServiceImpl(ProductRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ProductVO add(final ProductVO product)
    {
        Product entity = mapper.map(product, Product.class);
        entity = repository.save(entity);
        return mapper.map(entity, ProductVO.class);
    }

    @Override
    public List<ProductVO> getProducts()
    {
        List<Product> entities = (List<Product>) repository.findAll();
        if (entities.size() < 1)
            throw new RecordDoesNotExistException();
        return mapper.mapList(entities, ProductVO.class);
    }

    @Override
    public List<ProductVO> getProductsByType(final String type)
    {
        List<Product> entities = repository.findByType(type);
        if (entities.size() < 1)
            throw new RecordDoesNotExistException();
        return mapper.mapList(entities, ProductVO.class);
    }

    @Override
    public List<ProductVO> getProductsByNameAndType(final String name, final String type)
    {
        List<Product> entities = repository.findByNameAndType(name, type);
        if (entities.size() < 1)
            throw new RecordDoesNotExistException();
        return mapper.mapList(entities, ProductVO.class);
    }

    @Override
    public void delete(final Long id)
    {
        Product product = repository.findOne(id);
        if (product == null)
            throw new RecordDoesNotExistException();
        repository.delete(product);
    }
}
