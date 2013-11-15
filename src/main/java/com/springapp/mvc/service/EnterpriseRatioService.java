package com.springapp.mvc.service;

import com.springapp.mvc.entity.EnterpriseRatio;

import java.util.List;

public interface EnterpriseRatioService {
    public Boolean userAlreadyVote(Integer userId, Integer enterpriseId);
   // public List<EnterpriseRatio> getTopList();
    public Integer getVoteValue(Integer enterpriseId);
    public Integer getVotes(Integer enterpriseId);
    public List<Integer> getVoteValuesByCategory(int categoryId);
    public void add(EnterpriseRatio enterpriseRatio);
    public double calculateSummaryRatio(Integer enterpriseId);
}
