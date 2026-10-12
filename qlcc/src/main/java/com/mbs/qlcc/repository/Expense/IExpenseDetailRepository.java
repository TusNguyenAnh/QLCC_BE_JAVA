package com.mbs.qlcc.repository.Expense;

import com.mbs.qlcc.domain.ExpenseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExpenseDetailRepository extends JpaRepository<ExpenseDetail, String> {
}
