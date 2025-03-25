package com.bankmanagement.bank_management_system.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.bankmanagement.bank_management_system.model.User;
import com.bankmanagement.bank_management_system.model.HibernateUtil.HibernateUtil;

public class UserDAO {
    @SuppressWarnings("deprecation")
	public void saveUser(User user) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            // Save the User object
            session.save(user);
            tx.commit(); // Commit the transaction
            System.out.println("User saved successfully!");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback(); // Rollback the transaction in case of error
            }
            System.err.println("Error saving user: " + e.getMessage());
            e.printStackTrace(); // Log the error for debugging
        } finally {
            if (session != null) {
                session.close(); // Always close the session
            }
        }
    }  

    public User getUserByEmail(String email) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            User user = session.createQuery("FROM User WHERE email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if an error occurs or the user doesn't exist
        } finally {
            if (session != null) {
                session.close(); // Always close the session
            }
        }
    }
}
