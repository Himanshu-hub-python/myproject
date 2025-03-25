package com.bankmanagement.bank_management_system;

import com.bankmanagement.bank_management_system.service.BankService;
import com.bankmanagement.bank_management_system.service.TransactionService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankService bankService = new BankService();
        TransactionService transactionService = new TransactionService();

        Integer loggedInUserId = null;

        while (true) {
            System.out.println("\n==== Bank Management System ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (choice == 1) {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                bankService.registerUser(name, email, password);
            } 
            else if (choice == 2) {
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                loggedInUserId = bankService.login(email, password);
                if (loggedInUserId != null) {
                    System.out.println("\nLogin successful! Welcome to your account.");
                    // Banking Operations
                    while (true) {
                        System.out.println("\n==== Banking Menu ====");
                        System.out.println("1. Deposit Money");
                        System.out.println("2. Withdraw Money");
                        System.out.println("3. Check Balance");
                        System.out.println("4. View Transaction History");
                        System.out.println("5. Logout");
                        System.out.print("Choose an option: ");
                        int bankChoice = scanner.nextInt();

                        if (bankChoice == 1) {
                            System.out.print("Enter Amount to Deposit: ");
                            double amount = scanner.nextDouble();
                            String result = transactionService.deposit(loggedInUserId, amount);
                            System.out.println(result);
                        } 
                        else if (bankChoice == 2) {
                            System.out.print("Enter Amount to Withdraw: ");
                            double amount = scanner.nextDouble();
                            String result = transactionService.withdraw(loggedInUserId, amount);
                            System.out.println(result);
                        } 
                        else if (bankChoice == 3) {
                            double balance = bankService.getBalance(loggedInUserId);
                            System.out.println("Your Current Balance: ₹" + balance);
                        } 
                        else if (bankChoice == 4) {
                            transactionService.viewTransactionHistory(loggedInUserId); // View transaction history
                        }
                        else if (bankChoice == 5) {
                            System.out.println("Logged out successfully!");
                            loggedInUserId = null;
                            break; // Break out of the banking menu
                        } 
                        else {
                            System.out.println("Invalid choice! Try again.");
                        }
                    }
                } else {
                    System.out.println("Invalid credentials! Try again.");
                }
            } 
            else if (choice == 3) {
                System.out.println("Exiting... Thank you!");
                break;
            } 
            else {
                System.out.println("Invalid choice! Please try again.");
            }
        }

        scanner.close(); // Close scanner after exiting the loop
    }
}
