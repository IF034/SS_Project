package com.springapp.mvc.service.impl;

import com.springapp.mvc.entity.City;
import com.springapp.mvc.repository.CityRepository;
import com.springapp.mvc.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CityServiceImpl implements CityService {

    public static final int PAGE_SIZE = 7;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    @Override
    public void delete(Integer cityId) {
        cityRepository.delete(cityId);
    }

    @Override
    public void add(City city) {
        cityRepository.save(city);
    }

    @Override
    public void update(City city) {
        cityRepository.save(city);
    }

    @Override
    public City get(int townId) {
        return cityRepository.findOne(townId);
    }

    @Override
    public Page<City> getCitiesPage(Integer pageNumber) {
        PageRequest pageRequest =
                new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "name");
        return cityRepository.findAll(pageRequest);
    }
}
