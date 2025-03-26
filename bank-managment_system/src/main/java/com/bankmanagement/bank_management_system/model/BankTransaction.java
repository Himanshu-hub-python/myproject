package com.bankmanagement.bank_management_system.model;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "transactions")
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sender_account_id", referencedColumnName = "id", nullable = false)
    private Account senderAccount;

    @ManyToOne
    @JoinColumn(name = "receiver_account_id", referencedColumnName = "id", nullable = false)
    private Account receiverAccount;

    @Column(nullable = false)
    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date transactionDate;

   @Column(nullable = false)
    private String transactionType;

    @Column(nullable = false)
    private String status;

    

    
}
