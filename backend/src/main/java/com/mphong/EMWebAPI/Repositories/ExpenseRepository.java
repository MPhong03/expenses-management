package com.mphong.EMWebAPI.Repositories;

import com.mphong.EMWebAPI.Models.Datas.Expense;
import com.mphong.EMWebAPI.Models.Datas.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
}
