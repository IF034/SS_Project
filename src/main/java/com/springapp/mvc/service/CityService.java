package com.springapp.mvc.service;

import com.springapp.mvc.entity.City;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CityService {

    List<City> getAll();

    void delete(Integer cityId);

    void add(City city);

    void update(City city);

    City get(int townId);

    Page<City> getCitiesPage(Integer pageNumber);
}
