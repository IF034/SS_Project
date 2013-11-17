package com.springapp.mvc.service;

import com.springapp.mvc.entity.City;

import java.util.List;

public interface CityService {

    List<City> getAll();

    void delete(Integer cityId);

    void add(City city);

    void update(City city);

    City get(int townId);
}
