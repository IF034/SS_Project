package com.springapp.mvc.repository;

import com.springapp.mvc.entity.ProposedEnterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposedEnterpriseRepository extends JpaRepository<ProposedEnterprise, Integer> {
}
