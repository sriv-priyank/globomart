package com.globomart.service;

import com.globomart.vo.ProductVO;

import java.util.List;

public interface ProductService {

    ProductVO add(ProductVO product);

    List<ProductVO> getProducts();

    List<ProductVO> getProductsByNameAndType(String name, String type);

    List<ProductVO> getProductsByType(String type);

    void delete(Long id);
}
