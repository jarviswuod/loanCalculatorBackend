package com.jarvis.loan.controller;

import com.jarvis.loan.entity.LoanInput;
import com.jarvis.loan.entity.Response;
import com.jarvis.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/calculate")
    public Response overallLoanDetails(@RequestBody LoanInput loanInput) {
        return loanService.overallLoanDetails(loanInput);
    }
}
