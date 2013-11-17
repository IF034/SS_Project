package com.springapp.mvc.service;

import com.springapp.mvc.entity.EnterpriseRatio;

import java.util.List;

public interface EnterpriseRatioService {
    Boolean userAlreadyVote(Integer userId, Integer enterpriseId);

    // public List<EnterpriseRatio> getTopList();
    Integer getVoteValue(Integer enterpriseId);

    Integer getVotes(Integer enterpriseId);

    List<Integer> getVoteValuesByCategory(int categoryId);

    void add(EnterpriseRatio enterpriseRatio);

    double calculateSummaryRatio(Integer enterpriseId);
}
