package com.banking.demo.service;

import com.banking.demo.Utils.AccountUtils;
import com.banking.demo.dto.*;
import com.banking.demo.entity.Transaction;
import com.banking.demo.entity.User;
import com.banking.demo.repository.TransactionRepository;
import com.banking.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    //check if user already exists/save the details of new user in the db
    public BankResponse createAccount(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            BankResponse bankResponse = BankResponse.builder()
                    .responseCode(AccountUtils.userExistsBankCode)
                    .responseMessage(AccountUtils.userExistsResponseMessage)
                    .accountInfo(null)
                    .build();
            return bankResponse;
        } else {
            User newuser = User.builder()
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .gender(userRequest.getGender())
                    .address(userRequest.getAddress())
                    .stateOfOrigin(userRequest.getStateOfOrigin())
                    .accountNumber(AccountUtils.generateAccountNumber())
                    .accountBalance(BigDecimal.ZERO)
                    .email(userRequest.getEmail())
                    .phoneNumber(userRequest.getPhoneNumber())
                    .status("Active")
                    .build();

            //saving in db(savedUser is the updated version of table which has saved data)
            User savedUser = userRepository.save(newuser);

            BankResponse bankResponse = BankResponse.builder()
                    .responseCode(AccountUtils.userSavedResponseCode)
                    .responseMessage(AccountUtils.userSavedResponseMessage)
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(savedUser.getAccountNumber())
                            .accountName(savedUser.getFirstName() + " " + savedUser.getLastName())
                            .accountBalance(savedUser.getAccountBalance())
                            .build())
                    .build();
            return bankResponse;
        }
    }

    @Override
    //check if this account exists/give the balance
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        boolean accountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!accountExists) {
            BankResponse bankResponse = BankResponse.builder()
                    .responseCode(AccountUtils.userNotExistsResponseCode)
                    .responseMessage(AccountUtils.userNotExistsResponseMessage)
                    .accountInfo(null)
                    .build();
            return bankResponse;
        } else {
            User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
            BankResponse bankResponse = BankResponse.builder()
                    .responseCode(AccountUtils.userFoundResponseCode)
                    .responseMessage(AccountUtils.userFoundResponseMessage)
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(foundUser.getAccountNumber())
                            .accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
                            .accountBalance(foundUser.getAccountBalance())
                            .build())
                    .build();
            return bankResponse;
        }
    }

    @Override
    public BankResponse creditAmount(CreditRequest creditRequest) {
        boolean accountExists = userRepository.existsByAccountNumber(creditRequest.getAccountNumber());
        if (!accountExists) {
            BankResponse bankResponse = BankResponse.builder()
                    .responseCode(AccountUtils.userNotExistsResponseCode)
                    .responseMessage(AccountUtils.userNotExistsResponseMessage)
                    .accountInfo(null)
                    .build();
            return bankResponse;
        } else {
            User foundUser = userRepository.findByAccountNumber(creditRequest.getAccountNumber());
            BigDecimal currBalance = foundUser.getAccountBalance();
            BigDecimal creditAmt = creditRequest.getAmount();

            foundUser.setAccountBalance(AccountUtils.addAmount(currBalance, creditAmt));

            //updating the db
            userRepository.save(foundUser);

            //updating the record in transactions table
            Transaction newTransaction=Transaction.builder()
                    .accountNumber1(creditRequest.getAccountNumber())
                    .accountBalance1(foundUser.getAccountBalance())
                    .status(AccountUtils.status)
                    .build();
            transactionRepository.save(newTransaction);

            BankResponse bankResponse = BankResponse.builder()
                    .responseCode(AccountUtils.amtCreditedCode)
                    .responseMessage(AccountUtils.amtCreditedMessage)
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(creditRequest.getAccountNumber())
                            .accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
                            .accountBalance(foundUser.getAccountBalance())
                            .build())
                    .build();

            return bankResponse;
        }
    }

    @Override
    public BankResponse debitAmount(CreditRequest creditRequest) {
        boolean accountExists = userRepository.existsByAccountNumber(creditRequest.getAccountNumber());
        if (!accountExists) {
            BankResponse bankResponse = BankResponse.builder()
                    .responseCode(AccountUtils.userNotExistsResponseCode)
                    .responseMessage(AccountUtils.userNotExistsResponseMessage)
                    .accountInfo(null)
                    .build();
            return bankResponse;
        } else {
            User foundUser = userRepository.findByAccountNumber(creditRequest.getAccountNumber());
            BigDecimal currBalance = foundUser.getAccountBalance();
            BigDecimal debitAmt = creditRequest.getAmount();

            if (debitAmt.compareTo(currBalance) == 1) {
                BankResponse bankResponse = BankResponse.builder()
                        .responseCode(AccountUtils.amtNotDebitedCode)
                        .responseMessage(AccountUtils.amtNotDebitedMessage)
                        .accountInfo(AccountInfo.builder()
                                .accountNumber(creditRequest.getAccountNumber())
                                .accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
                                .accountBalance(foundUser.getAccountBalance())
                                .build())
                        .build();
                return bankResponse;
            } else {

                foundUser.setAccountBalance(AccountUtils.deductAmount(currBalance, debitAmt));

                //saving the updated accountBalance
                userRepository.save(foundUser);

                //updating the record in transactions table
                Transaction newTransaction=Transaction.builder()
                        .accountNumber1(creditRequest.getAccountNumber())
                        .accountBalance1(foundUser.getAccountBalance())
                        .status(AccountUtils.status)
                        .build();
                transactionRepository.save(newTransaction);

                BankResponse bankResponse = BankResponse.builder()
                        .responseCode(AccountUtils.amtDebitedCode)
                        .responseMessage(AccountUtils.amtDebitedMessage)
                        .accountInfo(AccountInfo.builder()
                                .accountNumber(creditRequest.getAccountNumber())
                                .accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
                                .accountBalance(foundUser.getAccountBalance())
                                .build())
                        .build();
                return bankResponse;
            }
        }

    }

    @Override
    public TransferBankResponse transferAmount(TransferRequest transferRequest) {
        boolean account1Exists = userRepository.existsByAccountNumber(transferRequest.getAccountNumber1());
        boolean account2Exists = userRepository.existsByAccountNumber(transferRequest.getAccountNumber2());
        if (!account1Exists || !account2Exists) {
            TransferBankResponse transferBankResponse1 = TransferBankResponse.builder()
                    .responseCode(AccountUtils.userNotExistsResponseCode)
                    .responseMessage(AccountUtils.userNotExistsResponseMessage)
                    .accountInfo1(null)
                    .accountInfo2(null)
                    .build();
            return transferBankResponse1;
        } else {
            //check if the account1 curr balance is sufficient for the debit request
            User account1 = userRepository.findByAccountNumber(transferRequest.getAccountNumber1());
            BigDecimal currBal_acct1 = account1.getAccountBalance();
            BigDecimal debitAmt = transferRequest.getTransferAmt();
            if (debitAmt.compareTo(currBal_acct1) == 1) {
                TransferBankResponse transferBankResponse2 = TransferBankResponse.builder()
                        .responseCode(AccountUtils.amtNotTranserferredCode)
                        .responseMessage(AccountUtils.amtNotTranserferredMessage)
                        .accountInfo1(AccountInfo.builder()
                                .accountNumber(transferRequest.getAccountNumber1())
                                .accountName(account1.getFirstName() + " " + account1.getLastName())
                                .accountBalance(account1.getAccountBalance())
                                .build())
                        .accountInfo2(AccountInfo.builder()
                                .accountNumber(transferRequest.getAccountNumber2())
                                .accountName(null)
                                .accountBalance(null)
                                .build())
                        .build();
                return transferBankResponse2;
            } else {
                //debit the amt from acct1 and credit to acct2
                User account2 = userRepository.findByAccountNumber(transferRequest.getAccountNumber2());
                BigDecimal currBal_acct2 = account2.getAccountBalance();

                //debit request
                account1.setAccountBalance(AccountUtils.deductAmount(currBal_acct1, debitAmt));
                //credit request
                account2.setAccountBalance(AccountUtils.addAmount(currBal_acct2, debitAmt));

                //save the updated amounts to db
                userRepository.save(account1);
                userRepository.save(account2);

                //updating the record in transactions table
                Transaction newTransaction=Transaction.builder()
                        .accountNumber1(transferRequest.getAccountNumber1())
                        .accountBalance1(account1.getAccountBalance())
                        .accountNumber2(transferRequest.getAccountNumber2())
                        .accountBalance2(account2.getAccountBalance())
                        .status(AccountUtils.status)
                        .build();
                transactionRepository.save(newTransaction);

                TransferBankResponse transferBankResponse3 = TransferBankResponse.builder()
                        .responseCode(AccountUtils.amtTranserferredCode)
                        .responseMessage(AccountUtils.amtTranserferredMessage)
                        .accountInfo1(AccountInfo.builder()
                                .accountNumber(transferRequest.getAccountNumber1())
                                .accountName(account1.getFirstName() + " " + account1.getLastName())
                                .accountBalance(account1.getAccountBalance())
                                .build())
                        .accountInfo2(AccountInfo.builder()
                                .accountNumber(transferRequest.getAccountNumber2())
                                .accountName(account2.getFirstName() + " " + account2.getLastName())
                                .accountBalance(account2.getAccountBalance())
                                .build())
                        .build();
                return transferBankResponse3;
            }
        }

    }

    @Override
    public String closeAccount(EnquiryRequest enquiryRequest) {
        boolean accountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!accountExists) {
            return "No existing account with this number!";
        } else {
            User foundUser=userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
            userRepository.delete(foundUser);
            return "User deleted successfully";
        }
    }
}


