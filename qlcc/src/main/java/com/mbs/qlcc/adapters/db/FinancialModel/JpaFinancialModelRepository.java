package com.mbs.qlcc.adapters.db.FinancialModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaFinancialModelRepository extends JpaRepository<FinancialModelDataMapper, String> {
    Optional<FinancialModelDataMapper> findByName(String name);
}
