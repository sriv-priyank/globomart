package com.globomart.service;

import com.globomart.vo.PriceVO;


public interface PricingService {

    PriceVO getProductPrice(String name, String type);

    PriceVO getPriceByProduct(Long productId);
}
