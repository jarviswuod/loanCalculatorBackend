package com.jarvis.loan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<LoanRepaymentSchedule> loanRepaymentSchedule;
    @ManyToOne
    @JoinColumn(name = "loan_summary_id")
    private LoanSummary loanSummary;

    public Response(LoanSummary loanSummary, List<LoanRepaymentSchedule> loanRepaymentSchedule) {
        this.loanRepaymentSchedule = loanRepaymentSchedule;
        this.loanSummary = loanSummary;
    }
}
