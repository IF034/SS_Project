package com.springapp.mvc.service;

import com.springapp.mvc.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    void delete(Integer categoryId);

    void add(Category category);

    void update(Category category);

    Page<Category> getCategoriesPage(Integer pageNumber);
}
