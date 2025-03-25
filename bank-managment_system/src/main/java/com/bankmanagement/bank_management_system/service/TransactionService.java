package com.bankmanagement.bank_management_system.service;

import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.bankmanagement.bank_management_system.dao.AccountDAO;
import com.bankmanagement.bank_management_system.dao.TransactionDAO;
import com.bankmanagement.bank_management_system.model.Account;
import com.bankmanagement.bank_management_system.model.BankTransaction;
import com.bankmanagement.bank_management_system.model.HibernateUtil.HibernateUtil;

public class TransactionService {
    
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    public TransactionService() {
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    // Withdraw Money
    public String withdraw(int accountId, double amount) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Fetch account by ID
            Account account = accountDAO.getAccountById(accountId);
            if (account == null) {
                return "Account not found!";
            }

            // Check if sufficient balance exists
            if (account.getBalance() < amount) {
                return "Insufficient balance!";
            }

            // Update account balance
            account.setBalance(account.getBalance() - amount);
            session.merge(account);

            // Create and persist the transaction object
            BankTransaction transaction = new BankTransaction();
            transaction.setSenderAccount(account); // Withdraw me sender account same hoga
            transaction.setReceiverAccount(null); // No receiver in case of withdrawal
            transaction.setAmount(amount);
            transaction.setTransactionDate(new Date());
            transaction.setTransactionType("Withdraw");
            transaction.setStatus("Completed");

            session.persist(transaction);

            tx.commit();
            return "Withdrawal successful!";
        } catch (HibernateException e) {
            e.printStackTrace();
            return "Error during withdrawal.";
        }
    }

    // Deposit Money
    public String deposit(int accountId, double amount) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Fetch account by ID
            Account account = accountDAO.getAccountById(accountId);
            if (account == null) {
                return "Account not found!";
            }

            // Update account balance
            account.setBalance(account.getBalance() + amount);
            session.merge(account);

            // Create and persist the transaction object
            BankTransaction transaction = new BankTransaction();
            transaction.setSenderAccount(account); // Deposit ke case me sender and receiver same hoga
            transaction.setReceiverAccount(account);
            transaction.setAmount(amount);
            transaction.setTransactionDate(new Date());
            transaction.setTransactionType("Deposit");
            transaction.setStatus("Completed");
       System.out.println(transaction);
          session.persist(transaction);

            tx.commit();
            return "Deposit successful!";
        } catch (HibernateException e) {
            e.printStackTrace();
            return "Error during deposit.";
        }
    }

    // View Transaction History
    public void viewTransactionHistory(int accountId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Fetch transactions associated with the given accountId
            Query<BankTransaction> query = session.createQuery(
                "FROM BankTransaction WHERE senderAccount.id = :accountId OR receiverAccount.id = :accountId", 
                BankTransaction.class);
            query.setParameter("accountId", accountId);

            List<BankTransaction> transactions = query.list();

            if (transactions.isEmpty()) {
                System.out.println("No transactions found for this account.");
            } else {
                System.out.println("\n==== Transaction History ====");
                for (BankTransaction transaction : transactions) {
                    System.out.println("Transaction Type: " + transaction.getTransactionType());
                    System.out.println("Amount: ₹" + transaction.getAmount());
                    System.out.println("Date: " + transaction.getTransactionDate());
                    System.out.println("Status: " + transaction.getStatus());
                    System.out.println("--------------------------------");
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}