package com.globomart.service.impl;

import com.globomart.config.AppProperties;
import com.globomart.domain.entity.Price;
import com.globomart.domain.repository.PriceRepository;
import com.globomart.mapper.Mapper;
import com.globomart.service.PricingService;
import com.globomart.service.exception.RecordDoesNotExistException;
import com.globomart.service.exception.ServiceException;
import com.globomart.vo.PriceVO;
import com.globomart.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@Service
final class PricingServiceImpl implements PricingService {

    private PriceRepository repository;
    private RestTemplate restTemplate;
    private AppProperties appProperties;
    private Mapper mapper;

    @Autowired
    public PricingServiceImpl(PriceRepository repository, RestTemplate restTemplate,
                              AppProperties appProperties, Mapper mapper) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.appProperties = appProperties;
        this.mapper = mapper;
    }

    private AppProperties.Service productServiceInfo;

    @PostConstruct
    public void init() {
        this.productServiceInfo = appProperties.getServices().stream()
                .filter(s -> "products".equals(s.getTitle()))
                .findFirst()
                .orElse(null);

        if (this.productServiceInfo == null) {
            throw new ServiceException("Service Configuration error - No service definition for Products",
                    new RuntimeException());
        }
    }

    @Override
    public PriceVO getProductPrice(String name, String type)
    {
        ProductVO[] vos = null;
        URI uri = getURIForProductsService(name, type);
        try {
            vos = restTemplate.getForObject(uri, ProductVO[].class);
        } catch (HttpStatusCodeException ex) {
            if (HttpStatus.NOT_FOUND != ex.getStatusCode())
                throw ex;
        }
        if (vos != null && vos.length > 0) {
            return getPriceByProduct(vos[0].getId());
        }

        throw new RecordDoesNotExistException();
    }

    @Override
    public PriceVO getPriceByProduct(Long productId)
    {
        Price price = repository.findByProductId(productId);
        if (price == null)
            throw new RecordDoesNotExistException();

        return mapper.map(price, PriceVO.class);
    }

    private URI getURIForProductsService(String name, String type) {
        Map<String, String> pathVars = new HashMap<>();
        pathVars.put("name", name);
        pathVars.put("type", type);

        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(productServiceInfo.getHostName())
                .path(productServiceInfo.getPath())
                .path("/name/{name}/type/{type}")
                .buildAndExpand(pathVars)
                .encode().toUri();
    }
}
