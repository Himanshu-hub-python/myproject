package com.bankmanagement.bank_management_system.service;

import com.bankmanagement.bank_management_system.dao.AccountDAO;
import com.bankmanagement.bank_management_system.dao.UserDAO;
import com.bankmanagement.bank_management_system.model.User;

public class BankService {
    private UserDAO userDAO = new UserDAO();
    
    public double getBalance(Integer userId) {
        AccountDAO accountDAO = new AccountDAO();
        return accountDAO.getBalanceByUserId(userId);
    }

    public void registerUser(String name, String email, String password) {
        User user = new User(0, email, name, password);
        userDAO.saveUser(user);
        System.out.println("User registered successfully!");
    }

    public Integer login(String email, String password) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user.getId(); // Return the user ID if login is successful
        }
        return null; // Return null if login fails
    }
}
