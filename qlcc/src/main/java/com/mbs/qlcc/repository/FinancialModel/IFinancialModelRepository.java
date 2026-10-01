package com.mbs.qlcc.repository.FinancialModel;

import com.mbs.qlcc.domain.FinancialModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFinancialModelRepository extends JpaRepository<FinancialModel, String> {
    Optional<FinancialModel> findByType(String type);
    Optional<FinancialModel> findByName(String name);
    Optional<FinancialModel> findByTypeAndDeletedAtIsNull(String type);
}
