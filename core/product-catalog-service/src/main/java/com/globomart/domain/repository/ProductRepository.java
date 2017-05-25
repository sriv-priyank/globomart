package com.globomart.domain.repository;

import com.globomart.domain.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByType(String type);

    List<Product> findByNameAndType(String name, String type);
}
