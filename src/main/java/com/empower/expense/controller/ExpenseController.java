package com.empower.expense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empower.expense.model.Expense;
import com.empower.expense.service.ExpenseService;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/expenses")
public class ExpenseController {

	@Autowired
    private ExpenseService expenseService;

    @GetMapping("/orderByDate")
    public ResponseEntity<List<Expense>> getAllExpensesOrderedByDateDesc() {
        List<Expense> expenses = expenseService.findAllOrderedByDateDescending();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.findAll();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long expenseId) {
        Expense expense = expenseService.findById(expenseId);
        if (expense != null) {
            return new ResponseEntity<>(expense, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/user/{userEmailId}")
    public ResponseEntity<List<Expense>> getExpensesByUserEmailId(@PathVariable String userEmailId) {
        List<Expense> expenses = expenseService.getExpensesByUserEmailId(userEmailId);
        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense createdExpense = expenseService.save(expense);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long expenseId, @RequestBody Expense expense) {
        Expense existingExpense = expenseService.findById(expenseId);
        if (existingExpense == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        expense.setExpenseId(expenseId);
        Expense updatedExpense = expenseService.save(expense);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        Expense expense = expenseService.findById(expenseId);
        if (expense != null) {
            expenseService.deleteById(expenseId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
