package com.bankmanagement.bank_management_system.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query; // Correct import for Hibernate Query

import com.bankmanagement.bank_management_system.model.Account;
import com.bankmanagement.bank_management_system.model.HibernateUtil.HibernateUtil;

public class AccountDAO {

    // Save a new account to the database
    public void saveAccount(Account account) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(account); // Save account to DB
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace(); // Log exception
        }
    }

    // Update an existing account in the database
    public void updateAccount(Account account) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(account); // Update account in DB
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace(); // Log exception
        }
    }

    // Get account by account ID
    public Account getAccountById(int accountId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Account.class, accountId); // Get account by ID
        } catch (Exception e) {
            e.printStackTrace(); // Log exception
            return null; // Return null in case of error
        }
    }

    // Get account by email
    public Account getAccountByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Account> query = session.createQuery("FROM Account WHERE email = :email", Account.class);
            query.setParameter("email", email);
            return query.uniqueResult(); // Get account by email
        } catch (Exception e) {
            e.printStackTrace(); // Log exception
            return null; // Return null in case of error
        }
    }

    // Delete an account from the database
    public void deleteAccount(int accountId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Account account = session.get(Account.class, accountId);
            if (account != null) {
                session.remove(account); // Delete account from DB
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace(); // Log exception
        }
    }

    // Get balance by user ID
    public double getBalanceByUserId(Integer userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("Fetching balance for User ID: " + userId);
            
            // Correct the query to select balance by user ID
            Query<Double> query = session.createQuery("SELECT a.balance FROM Account a WHERE a.user.id = :userId", Double.class);
            query.setParameter("userId", userId);
            Double balance = query.uniqueResult();
            
            if (balance != null) {
                System.out.println("Balance fetched: " + balance);
                return balance;
            } else {
                System.out.println("No account found for User ID: " + userId);
                return 0.0; // Return 0.0 if no account found
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log exception
            return 0.0; // Return 0.0 in case of error
        }
    }

}
