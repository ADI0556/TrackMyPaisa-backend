package com.empower.expense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empower.expense.model.Expense;
import com.empower.expense.repository.ExpenseRepository;

@Service
public class ExpenseService {

	@Autowired
    private ExpenseRepository expenseRepository;

    public Expense findById(Long expenseId) {
    	Optional<Expense> temp = expenseRepository.findById(expenseId);
    	Expense expense=null;
    	if(temp.isPresent())
    	{
    		expense=temp.get();
    	}
    	return expense;
    			
//        return expenseRepository.findById(expenseId).orElse(null);
    }

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public List<Expense> getExpensesByUserEmailId(String userEmailId) {
        return expenseRepository.findByUserEmailId(userEmailId);
    }
    
    public List<Expense> findAllOrderedByDateDescending() {
        return expenseRepository.findAllByOrderByDateDesc();
    }

    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void deleteById(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }
}
