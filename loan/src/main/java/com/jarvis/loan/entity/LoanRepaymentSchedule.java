package com.jarvis.loan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LoanRepaymentSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double startingBalance;
    private double interest;
    private double principal;
    private double endingBalance;

    public LoanRepaymentSchedule(double balance, double interest, double principal, double endingBalance) {
        this.startingBalance = balance;
        this.interest = interest;
        this.principal = principal;
        this.endingBalance = endingBalance;
    }
}
