package com.springapp.mvc.service.impl;

import com.springapp.mvc.entity.Category;
import com.springapp.mvc.repository.CategoryRepository;
import com.springapp.mvc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

   @Autowired
   private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void delete(Integer categoryId) {
        categoryRepository.delete(categoryId);
    }

    @Override
    public void add(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void update(Category category) {
        categoryRepository.save(category);
    }
}
