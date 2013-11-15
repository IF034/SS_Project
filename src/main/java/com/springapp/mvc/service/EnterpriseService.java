package com.springapp.mvc.service;

import com.springapp.mvc.entity.Comment;
import com.springapp.mvc.entity.Enterprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface EnterpriseService {

    public int countEnterpriseByCategory(int categoryId);
    public int countEnterpriseByCity(int cityId);
    public void addComment(Comment comment);
    public List<Comment> getComments(int firmId);
    public Comment getEmptyComment(Integer userId, Integer enterpriseId);
    public void updateComment(Comment comment);
    public Comment getComment(Integer id);
    public long countEnterprises(Integer id, int townId);
    public List<Integer> countEnterprisesInAllCities();
    public Page<Enterprise> getAllByForPage(int categoryId,
                                            int townId,
                                            int currentPage,
                                            int maxElementsOnPage,
                                            Sort.Direction direction,
                                            String propertyName);
    public List<Integer> countEnterprisesInCategories();
    public void add(Enterprise enterprise);
    public Enterprise get(int firmId);
    public void delete(int firmId);
    public void update(Enterprise enterprise);
    public List<Enterprise> getAll();
    public List<Enterprise> getAllEnterprisesInCategories();
    public long countItems();
    public Page<Enterprise> getEnterprisePage(Integer pageNumber);
    public void calculateSummaryRatio(Integer enterpriseId);
    public List<Enterprise> getTopList(int itemCount);
    public Page<Enterprise> getAllByForPage(Integer categoryId, Integer cityId, Pageable pageable);
}


