package com.banking.demo.controller;

import com.banking.demo.dto.*;
import com.banking.demo.service.UserService;
import com.banking.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/adduser")
    public BankResponse createUser(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @GetMapping("/checkbalance")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return userService.balanceEnquiry(enquiryRequest);
    }

    @PostMapping("/credit")
    public BankResponse credit(@RequestBody CreditRequest creditRequest){
        return userService.creditAmount(creditRequest);
    }

    @PostMapping("/debit")
    public BankResponse debit(@RequestBody CreditRequest creditRequest){
        return userService.debitAmount(creditRequest);
    }

    @PostMapping("/transfer")
    public TransferBankResponse transfer(@RequestBody TransferRequest transferRequest){
        return userService.transferAmount(transferRequest);
    }

    @PostMapping("/close")
    public String close(@RequestBody EnquiryRequest enquiryRequest) {
        return userService.closeAccount(enquiryRequest);
    }

}
