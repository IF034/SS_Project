package com.springapp.mvc.service;

import com.springapp.mvc.entity.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> getAll();
    public void delete(Integer categoryId);
    public void add(Category category);
    public void update(Category category);
}
