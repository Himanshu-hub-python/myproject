package com.bankmanagement.bank_management_system.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bankmanagement.bank_management_system.model.HibernateUtil.HibernateUtil;

public class TransactionDAO {

    // Save a transaction to the database
    public void saveTransaction(Transaction transaction) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(transaction);  

        tx.commit();
        session.close();
    }

    // Get a transaction by ID
    public Transaction getTransactionById(int transactionId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.get(Transaction.class, transactionId);
        session.close();
        return transaction;
    }
}