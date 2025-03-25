package com.bankmanagement.bank_management_system.model.HibernateUtil;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.bankmanagement.bank_management_system.model.Account;
import com.bankmanagement.bank_management_system.model.User;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class).addAnnotatedClass(Account.class).buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex); // Throw error if session factory fails to build
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory; // Return session factory instance
    }
}
