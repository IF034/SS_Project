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

//    @Override
//    public List<EnterpriseRatio> getTopList() {
////       List<EnterpriseRatio> topList = enterpriseRatioRepository.findAll();
////        for (int i = 0; i<topList.size(); i++){
////            for (int j = 0; j<topList.size(); j++){
////                if (!(i==j)) {
////                    if (topList.get(i).getEnterprise().getId().equals(topList.get(j).getEnterprise().getId())){
////                    topList.get(i).setValue(Math.round((topList.get(i).getValue()+topList.get(j).getValue())/2.0f));
////                        topList.remove(j);
////                        j--;
////                    }
////                }
////            }
////        }
////        Collections.sort(topList, new Comparator<EnterpriseRatio>() {
////            public int compare(EnterpriseRatio a1, EnterpriseRatio a2) {
////                return a2.getValue() - a1.getValue();
////            }
////        });
////        if (topList.size()> MAX_TOPLIST_ITEM_NUMBER) {
////            for (int k = NUMBER_OF_FIRS_UNUSER_ITEM_FOR_TOPLIST; k<topList.size(); k++){
////                topList.remove(k);
////                k--;
////            }
////        }
//             System.out.println(enterpriseRatioRepository.getTopList());
//        return enterpriseRepository.getTopList();
//    }

    @Override
    public Boolean userAlreadyVote(Integer userId, Integer enterpriseId) {
        if (userId.equals(0)) {
            return true;
        }
        return enterpriseRatioRepository.userAlreadyVote(userId, enterpriseId);
    }
    @Override
    public Integer getVoteValue(Integer enterpriseId){

        Integer votes = getVotes(enterpriseId);
        if (votes==0)
            return 0;
        Long voteValue = enterpriseRatioRepository.getVoteValue(enterpriseId);
        if (votes.equals(null)) return 0;
            return Math.round(voteValue/votes);
    }
    @Override
    public Integer getVotes(Integer enterpriseId){

         return Integer.valueOf((int)enterpriseRatioRepository.getVotes(enterpriseId));
    }

    public List<Integer> getVoteValuesByCategory(int categoryId){
//        List<EnterpriseRatio> listEnterpriseRatio = enterpriseRatioRepository.findAll();
//        List<Integer> result = new ArrayList<Integer>();
//        for (int i = 0; i < listEnterpriseRatio.size(); i++) {
//            if (listEnterpriseRatio.get(i).getEnterprise().getCategory().getId().equals(categoryId)){
//                result.add(getVoteValue(i));
//            }
//        }
        List<EnterpriseRatio> listEnterpriseRatio = enterpriseRatioRepository.getEnterpriseRatioByCategory(categoryId);
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i<listEnterpriseRatio.size(); i++) {
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
