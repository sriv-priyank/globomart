package com.globomart.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    private final ObjectMapper mapper;

    Mapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T map(Object obj, Class<T> clazz)
    {
        return mapper.convertValue(obj, clazz);
    }

    public <T> List<T> mapList(List<?> sourceList, Class<T> clazz)
    {
        return sourceList.stream()
                .map(p -> this.map(p, clazz))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
