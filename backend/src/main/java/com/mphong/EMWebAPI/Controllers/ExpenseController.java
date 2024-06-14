package com.mphong.EMWebAPI.Controllers;

import com.mphong.EMWebAPI.Models.Datas.Expense;
import com.mphong.EMWebAPI.Models.Datas.User;
import com.mphong.EMWebAPI.Services.ExpenseService;
import com.mphong.EMWebAPI.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(@RequestParam Long userId) {
        User user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Expense> expenses = expenseService.getAllExpenses(user);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        return ResponseEntity.ok(expense);
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense createdExpense = expenseService.addExpense(expense);
        return ResponseEntity.ok(createdExpense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expenseDetails) {
        Expense updatedExpense = expenseService.updateExpense(id, expenseDetails);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok().build();
    }
}
