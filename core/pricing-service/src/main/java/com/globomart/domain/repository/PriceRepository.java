package com.globomart.domain.repository;

import com.globomart.domain.entity.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PriceRepository extends CrudRepository<Price, Long> {

    Price findByProductId(Long productId);
}
