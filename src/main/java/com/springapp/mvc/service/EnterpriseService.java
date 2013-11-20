package com.springapp.mvc.service;

import com.springapp.mvc.entity.Comment;
import com.springapp.mvc.entity.Enterprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface EnterpriseService {

    int countEnterpriseByCategory(int categoryId);

    int countEnterpriseByCity(int cityId);

    void addComment(Comment comment);

    List<Comment> getComments(int firmId);

    Comment getEmptyComment(Integer userId, Integer enterpriseId);

    void updateComment(Comment comment);

    Comment getComment(Integer id);

    long countEnterprises(Integer id, int townId);

    List<Integer> countEnterprisesInAllCities();

    Page<Enterprise> getAllByForPage(int categoryId,
                                     int townId,
                                     int currentPage,
                                     int maxElementsOnPage,
                                     Sort.Direction direction,
                                     String propertyName);

    List<Integer> countEnterprisesInCategories();

    void add(Enterprise enterprise);

    Enterprise get(int firmId);

    void delete(int firmId);

    void update(Enterprise enterprise);

    List<Enterprise> getAll();

    List<Enterprise> getAllEnterprisesInCategories();

    long countItems();

    Page<Enterprise> getEnterprisePage(Integer pageNumber);

    void calculateSummaryRatio(Integer enterpriseId);

    List<Enterprise> getTopList(int itemCount);

    Page<Enterprise> getAllByForPage(Integer categoryId, Integer cityId, Pageable pageable);

    double getVoteValue(Integer enterprise);
}


