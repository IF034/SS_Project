package com.springapp.mvc.service;

import com.springapp.mvc.entity.City;

import java.util.List;

public interface CityService {

   public List<City> getAll();
    public void delete(Integer cityId);
    public void add(City city);
    public void update(City city);
    public City get(int townId);
}
