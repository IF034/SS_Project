package com.springapp.mvc.service.impl;

import com.springapp.mvc.entity.EnterpriseRatio;
import com.springapp.mvc.repository.EnterpriseRatioRepository;
import com.springapp.mvc.service.EnterpriseRatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EnterpriseRatioServiceImpl implements EnterpriseRatioService {

    public static final int MAX_TOPLIST_ITEM_NUMBER = 9;
    public static final int NUMBER_OF_FIRS_UNUSER_ITEM_FOR_TOPLIST = 10;
    @Autowired
    private EnterpriseRatioRepository enterpriseRatioRepository;

    @Override
    public Boolean userAlreadyVote(Integer userId, Integer enterpriseId) {
        if (userId.equals(0)) {
            return true;
        }
        return enterpriseRatioRepository.userAlreadyVote(userId, enterpriseId);
    }

    @Override
    public Integer getVoteValue(Integer enterpriseId) {

        Integer votes = getVotes(enterpriseId);
        if (votes == 0) {
            return 0;
        }
        Long voteValue = enterpriseRatioRepository.getVoteValue(enterpriseId);

        return Math.round(voteValue / votes);
    }

    @Override
    public Integer getVotes(Integer enterpriseId) {

        return Integer.valueOf((int) enterpriseRatioRepository.getVotes(enterpriseId));
    }

    public List<Integer> getVoteValuesByCategory(int categoryId) {

        List<EnterpriseRatio> listEnterpriseRatio = enterpriseRatioRepository.getEnterpriseRatioByCategory(categoryId);
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < listEnterpriseRatio.size(); i++) {
            result.add(getVoteValue(i));
        }
        return result;
    }

    @Override
    public void add(EnterpriseRatio enterpriseRatio) {
        enterpriseRatioRepository.save(enterpriseRatio);
    }

    @Override
    public double calculateSummaryRatio(Integer enterpriseId) {
        return enterpriseRatioRepository.calculateSummaryRatioForEnterprise(enterpriseId);
    }

}
