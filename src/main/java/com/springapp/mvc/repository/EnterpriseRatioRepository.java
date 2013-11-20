package com.springapp.mvc.repository;

import com.springapp.mvc.entity.EnterpriseRatio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterpriseRatioRepository extends JpaRepository<EnterpriseRatio, Integer> {
    @Query("select case when (count(er) > 0)  then true else false end "
            + "from EnterpriseRatio er where er.user.id = ?1 and er.enterprise.id = ?2 ")
    Boolean userAlreadyVote(int userId, int enterpriseId);

    @Query("select count(er) from EnterpriseRatio er where er.enterprise.id = ?1")
    long getVotes(int enterpriseId);

    @Query("select sum(er.value) from EnterpriseRatio er where er.enterprise.id = ?1 ")
    long getVoteValue(int enterpriseId);

    @Query("select er from EnterpriseRatio er where er.enterprise.category.id = ?1 ")
    List<EnterpriseRatio> getEnterpriseRatioByCategory(int categoryId);

    @Query("select avg(er.value) from EnterpriseRatio er where er.enterprise.id =?1")
    double calculateSummaryRatioForEnterprise(Integer enterpriseId);

}
