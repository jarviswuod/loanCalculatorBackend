package com.jarvis.loan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LoanSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double paymentEveryMonth;
    private double totalPaybackPayments;
    private double totalInterest;

    public LoanSummary(double paymentEveryMonth, double totalPaybackPayments, double totalInterest) {
        this.paymentEveryMonth = paymentEveryMonth;
        this.totalPaybackPayments = totalPaybackPayments;
        this.totalInterest = totalInterest;
    }
}