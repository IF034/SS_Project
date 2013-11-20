package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Enterprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {

    @Query("select e from Enterprise e where (?1=0 or ?1=e.category.id) and (?2=0 or ?2=e.city.id)")
    Page<Enterprise> getAllByForPage(Integer categoryId,
                                     Integer townId,
                                     Pageable pageable);

    @Query("select e from Enterprise e where e.category.id=?1")
    Page<Enterprise> getAllByCategoryForPage(int categoryId, Pageable pageable);

    @Query("select e from Enterprise e where e.city.id=?1")
    Page<Enterprise> getAllByCityForPage(int townId, Pageable pageable);

    @Query("select count(e) from Enterprise e where e.category.id = ?1")
    long countEnterpriseByCategory(int categoryId);

    @Query("select count(e) from Enterprise e where e.city.id = ?1")
    long countEnterpriseByCity(int cityId);

    @Query("select count(e) from Enterprise e where (?1=0 or ?1=e.category.id) and (?2=0 or ?2=e.city.id)")
    long countEnterprises(int categoryId, int cityId);

    @Query("select count(e) from Enterprise e where e.category.id = ?1")
    long countEnterprises(int categoryId);

    @Query("select e from Enterprise e order by e.summaryRatio desc")
    Page<Enterprise> getTopEnterprisesByRatio(Pageable pageable);

    @Modifying
    @Query("update Enterprise e set e.summaryRatio = ?2 where e.id = ?1")
    void updateSummaryRatioForEnterprise(Integer enterpriseId, double summaryRatio);

    @Query("select e.summaryRatio from Enterprise e where e.id = ?1")
    double getVoteValue(Integer enterprise);
}
