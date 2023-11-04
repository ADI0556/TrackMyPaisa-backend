package com.empower.expense.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.empower.expense.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Additional query method to find all expenses ordered by expense date in descending order
    List<Expense> findAllByOrderByDateDesc();
    
    @Query("select e from Expense e where e.users.emailId=:emailId")
    List<Expense> findByUserEmailId(@Param("emailId")String  userEmailId);

}
