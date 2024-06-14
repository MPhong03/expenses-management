package com.mphong.EMWebAPI;

import com.mphong.EMWebAPI.Models.Datas.Expense;
import com.mphong.EMWebAPI.Models.Datas.User;
import com.mphong.EMWebAPI.Repositories.ExpenseRepository;
import com.mphong.EMWebAPI.Services.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ExpenseServiceTest {
    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    private Expense expense;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserID(1L);

        expense = new Expense();
        expense.setExpenseID(1L);
        expense.setAmount(100.0);
        expense.setDescription("Test Expense");
        expense.setUser(user);
    }

    @Test
    void testGetAllExpenses() {
        when(expenseRepository.findByUser(user)).thenReturn(List.of(expense));
        assertEquals(1, expenseService.getAllExpenses(user).size());
    }

    @Test
    void testGetExpenseById() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        assertEquals(expense, expenseService.getExpenseById(1L).orElse(null));
    }

    @Test
    void testAddExpense() {
        when(expenseRepository.save(expense)).thenReturn(expense);
        assertEquals(expense, expenseService.addExpense(expense));
    }

    @Test
    void testUpdateExpense() {
        Expense updatedExpense = new Expense();
        updatedExpense.setAmount(200.0);
        updatedExpense.setDescription("Updated Test Expense");

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        when(expenseRepository.save(expense)).thenReturn(expense);

        Expense result = expenseService.updateExpense(1L, updatedExpense);
        assertEquals(200.0, result.getAmount());
        assertEquals("Updated Test Expense", result.getDescription());
    }

    @Test
    void testDeleteExpense() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        doNothing().when(expenseRepository).delete(expense);
        expenseService.deleteExpense(1L);
        verify(expenseRepository, times(1)).delete(expense);
    }
}
