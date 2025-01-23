package com.jarvis.loan.entity;

import lombok.Getter;


@Getter
public class LoanInput {

    private double loanAmount;
    private double annualInterestRate;
    private int termInYears;
    private String compoundingType;
    private String paybackFrequency;

}
