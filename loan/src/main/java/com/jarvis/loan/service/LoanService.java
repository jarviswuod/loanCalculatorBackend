package com.jarvis.loan.service;

import com.jarvis.loan.entity.LoanInput;
import com.jarvis.loan.entity.LoanRepaymentSchedule;
import com.jarvis.loan.entity.LoanSummary;
import com.jarvis.loan.entity.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    public LoanSummary provideLoanSummary(LoanInput loanInput) {
        double loanAmount = loanInput.getLoanAmount();
        int termInYears = loanInput.getTermInYears();
        String paybackFrequency = loanInput.getPaybackFrequency();


        double paymentEveryMonth = calculatePayment(loanInput);
        int totalPeriods = getTotalPeriods(termInYears, paybackFrequency);
        double totalPaybackPayments = paymentEveryMonth * totalPeriods;
        double totalInterest = totalPaybackPayments - loanAmount;


        LoanSummary loanSummary = new LoanSummary(paymentEveryMonth, totalPaybackPayments, totalInterest);

        return loanSummary;
    }


    double calculatePayment(LoanInput loanInput) {
        double loanAmount = loanInput.getLoanAmount();
        double annualInterestRate = loanInput.getAnnualInterestRate();
        int termInYears = loanInput.getTermInYears();
        String compoundingType = loanInput.getCompoundingType();
        String paybackFrequency = loanInput.getPaybackFrequency();

        double payment = 0.0;
        double ratePerPeriod = getRatePerPeriod(annualInterestRate, compoundingType);
        int totalPeriods = getTotalPeriods(termInYears, paybackFrequency);

        if (compoundingType.equals("continuously")) {
            payment = loanAmount * ratePerPeriod / (1 - Math.exp(-ratePerPeriod * totalPeriods));
        } else {
            payment = loanAmount * ratePerPeriod / (1 - Math.pow(1 + ratePerPeriod, -totalPeriods));
        }

        return payment;
    }

    // Function to get the rate per period based on compounding type
    double getRatePerPeriod(double annualInterestRate, String compoundingType) {
        switch (compoundingType) {
            case "yearly":
                return annualInterestRate / 1;
            case "quarterly":
                return annualInterestRate / 4;
            case "semi-annually":
                return annualInterestRate / 2;
            case "monthly":
                return annualInterestRate / 12;
            case "semi-monthly":
                return annualInterestRate / 24;
            case "biweekly":
                return annualInterestRate / 26;
            case "weekly":
                return annualInterestRate / 52;
            case "daily":
                return annualInterestRate / 365;
            case "continuously":
                return Math.log(1 + annualInterestRate);
            default:
                return annualInterestRate / 12; // monthly by default
        }
    }

    // Function to get the total number of periods based on payback frequency
    int getTotalPeriods(int termInYears, String paybackFrequency) {
        switch (paybackFrequency) {
            case "every day":
                return termInYears * 365;
            case "every week":
                return termInYears * 52;
            case "fortnightly":
                return termInYears * 26;
            case "every half month":
                return termInYears * 24;
            case "every month":
                return termInYears * 12;
            case "every quarter":
                return termInYears * 4;
            case "every 6 months":
                return termInYears * 2;
            case "every year":
                return termInYears * 1;
            default:
                return termInYears * 12; // monthly by default
        }
    }


    public Response overallLoanDetails(LoanInput loanInput) {
        LoanSummary loanSummary = provideLoanSummary(loanInput);
        List<LoanRepaymentSchedule> loanRepaymentSchedule = calculateAmortizationSchedule(loanInput);
        Response response = new Response(loanSummary, loanRepaymentSchedule);
        return response;
    }

    // Function to calculate the amortization schedule
    public List<LoanRepaymentSchedule> calculateAmortizationSchedule(LoanInput loanInput) {
        double loanAmount = loanInput.getLoanAmount();
        double annualInterestRate = loanInput.getAnnualInterestRate();
        int termInYears = loanInput.getTermInYears();
        String compoundingType = loanInput.getCompoundingType();
        String paybackFrequency = loanInput.getPaybackFrequency();

        double payment = calculatePayment(loanInput);
        double balance = loanAmount;
        double ratePerPeriod = getRatePerPeriod(annualInterestRate, compoundingType);
        int totalPeriods = getTotalPeriods(termInYears, paybackFrequency);
        List<LoanRepaymentSchedule> loanRepaymentSchedule = new ArrayList<>();

        for (int period = 1; period <= totalPeriods; period++) {
            double interest = balance * ratePerPeriod;
            double principal = payment - interest;
            double endingBalance = balance - principal;


            balance = endingBalance;

            loanRepaymentSchedule.add(new LoanRepaymentSchedule(balance, interest, principal, endingBalance));
        }
        return loanRepaymentSchedule;
    }
}
