package com.springapp.mvc.service.impl;

import com.springapp.mvc.entity.*;
import com.springapp.mvc.repository.EnterpriseRepository;
import com.springapp.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EnterpriseServiceImpl implements EnterpriseService {

    public static final int PAGE_SIZE = 2;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private EnterpriseRatioService enterpriseRatioService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CityService cityService;

    @Override
    public Page<Enterprise> getAllByForPage(int categoryId, int townId, int currentPage, int maxElementsOnPage, Sort.Direction direction, String propertyName) {
        return enterpriseRepository.getAllByForPage(categoryId, townId,
                new PageRequest(currentPage, maxElementsOnPage, direction, propertyName));
    }

    @Override
    public int countEnterpriseByCity(int cityId) {
        return (int) enterpriseRepository.countEnterpriseByCity(cityId);
    }
/*
    @Override
    public List<Enterprise> getScopeByCategory(int firstIndex, int numberOfItems, int categoryId) {
        return enterpriseRepository.getScopeByCategory(firstIndex, numberOfItems, categoryId);
    }*/


    @Override
    public int countEnterpriseByCategory(int categoryId) {
        return (int) enterpriseRepository.countEnterpriseByCategory(categoryId);
    }

  /*  @Override
    public int numberOfPagesForPagination(int maxNumberOfItemsOnPage, int categoryId){
        if (countEnterpriseByCategory(categoryId)%maxNumberOfItemsOnPage!=0){
            return countEnterpriseByCategory(categoryId)/maxNumberOfItemsOnPage+1;
        } else{
            return countEnterpriseByCategory(categoryId)/maxNumberOfItemsOnPage;
        }
    }*/

    @Override
    public void addComment(Comment comment) {
        commentService.add(comment);
    }

    @Override
    public List<Comment> getComments(int enterpriseId) {
        return commentService.getAllForEnterprise(enterpriseId);
    }

    @Override
    public Comment getEmptyComment(Integer userId, Integer enterpriseId) {
        Enterprise enterprise = new Enterprise();
        enterprise.setId(enterpriseId);
        User user = new User();
        user.setId(userId);
        Comment comment = new Comment();
        comment.setEnterprise(enterprise);
        comment.setUser(user);
        return comment;
    }

    @Override
    public void updateComment(Comment comment) {
        commentService.update(comment);
    }

    @Override
    public Comment getComment(Integer id) {
        return commentService.get(id);
    }

    @Override
    public List<Integer> countEnterprisesInCategories() {
        List<Integer> result = new ArrayList<Integer>();
        for (Category category : categoryService.getAll()) {
            result.add(countEnterpriseByCategory(category.getId()));
        }
        return result;
    }

    @Override
    public void add(Enterprise enterprise) {
        enterpriseRepository.save(enterprise);
    }

    @Override
    public Enterprise get(int firmId) {
        return enterpriseRepository.findOne(firmId);
    }

    @Override
    public void delete(int firmId) {
        enterpriseRepository.delete(firmId);
    }

    @Override
    public void update(Enterprise enterprise) {
        enterpriseRepository.save(enterprise);
    }

    @Override
    public List<Enterprise> getAll() {
        return enterpriseRepository.findAll();
    }

    @Override
    public List<Enterprise> getAllEnterprisesInCategories() {
        return enterpriseRepository.findAll();
    }

    @Override
    public long countItems() {
        return enterpriseRepository.count();
    }

    @Override
    public long countEnterprises(Integer id, int townId) {
        if (townId == 0 && id == 0) {
            return enterpriseRepository.count();
        } else if (townId == 0) {
            return enterpriseRepository.countEnterpriseByCategory(id);
        } else if (id == 0) {
            return enterpriseRepository.countEnterpriseByCity(townId);
        } else {
            return enterpriseRepository.countEnterprises(id, townId);
        }
    }

    @Override
    public List<Integer> countEnterprisesInAllCities() {
        List<Integer> result = new ArrayList<Integer>();
        for (City city : cityService.getAll()) {
            result.add(countEnterpriseByCity(city.getId()));
        }
        return result;
    }

    @Override
    public Page<Enterprise> getEnterprisePage(Integer pageNumber) {
        PageRequest pageRequest =
                new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "name");
        return enterpriseRepository.findAll(pageRequest);
    }

    @Override
    public void calculateSummaryRatio(Integer enterpriseId) {
        enterpriseRepository.updateSummaryRatioForEnterprise(enterpriseId,
                enterpriseRatioService.calculateSummaryRatio(enterpriseId));
    }

    @Override
    public List<Enterprise> getTopList(int count) {
        return enterpriseRepository.getTopEnterprisesByRatio(new PageRequest(0, count)).getContent();
    }

    @Override
    public Page<Enterprise> getAllByForPage(Integer categoryId, Integer cityId, Pageable pageable) {
        return enterpriseRepository.getAllByForPage(categoryId, cityId, pageable);
    }
}
