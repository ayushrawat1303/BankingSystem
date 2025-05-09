package com.banking.demo.service;

import com.banking.demo.dto.*;

import java.math.BigDecimal;

public interface UserService {
     BankResponse createAccount(UserRequest userRequest);
     BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
     BankResponse creditAmount(CreditRequest creditRequest);
     BankResponse debitAmount(CreditRequest creditRequest);
     TransferBankResponse transferAmount(TransferRequest transferRequest);
     String closeAccount(EnquiryRequest enquiryRequest);
}
