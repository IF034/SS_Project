package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Enterprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {

    @Query("select e from Enterprise e where (?1=0 or ?1=e.category.id) and (?2=0 or ?2=e.city.id)")
    public Page<Enterprise> getAllByForPage(Integer categoryId,
                                            Integer townId,
                                            Pageable pageable);

    @Query("select e from Enterprise e where e.category.id=?1")
    public Page<Enterprise> getAllByCategoryForPage(int categoryId, Pageable pageable);

    @Query("select e from Enterprise e where e.city.id=?1")
    public Page<Enterprise> getAllByCityForPage(int townId, Pageable pageable);

    @Query("select count(e) from Enterprise e where e.category.id = ?1")
    public long countEnterpriseByCategory(int categoryId);

    @Query("select count(e) from Enterprise e where e.city.id = ?1")
    public long countEnterpriseByCity(int cityId);

    @Query("select count(e) from Enterprise e where e.category.id = ?1 and e.city.id = ?2")
    public long countEnterprises(int categoryId, int cityId);

    @Query("select count(e) from Enterprise e where e.category.id = ?1")
    public long countEnterprises(int categoryId);

    @Query("select e from Enterprise e order by e.summaryRatio asc")
    public Page<Enterprise> getTopEnterprisesByRatio(Pageable pageable);

    @Query("update Enterprise e set e.summaryRatio = ?2 where e.id = ?1")
    public void updateSummaryRatioForEnterprise(Integer enterpriseId, double summaryRatio);
}
