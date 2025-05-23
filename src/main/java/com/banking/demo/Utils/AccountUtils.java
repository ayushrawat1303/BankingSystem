package com.banking.demo.Utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Year;

public class AccountUtils {


    public static String generateAccountNumber()
    {
        Year year=Year.now();
        int min=000000;int max=999999;
        int randomInt = (int)(min + (max - min + 1) * Math.random());
        StringBuilder sb = new StringBuilder();
        sb.append(year).append(randomInt);
        String generatedAccountNumber = sb.toString();
        return generatedAccountNumber;
    }

    public static BigDecimal addAmount(BigDecimal currBalance,BigDecimal creditAmount){
        BigDecimal updatedBalance=currBalance.add(creditAmount);
        return updatedBalance;
    }
    public static BigDecimal deductAmount(BigDecimal currBalance,BigDecimal debitAmount){
        BigDecimal updatedBalance=currBalance.subtract(debitAmount);
        return updatedBalance;
    }
    public static final String status="Completed";
    public static final String userExistsBankCode="001";
    public static final String userExistsResponseMessage= "User already exists!";
    public static final String userSavedResponseCode="002";
    public static final String userSavedResponseMessage= "User details saved successfully!";
    public static final String userNotExistsResponseMessage= "User not found!";
    public static final String userNotExistsResponseCode= "003";
    public static final String userFoundResponseMessage= "User found!";
    public static final String userFoundResponseCode= "004";
    public static final String amtCreditedMessage= "Amount credited successfully!";
    public static final String amtCreditedCode= "005";
    public static final String amtDebitedMessage= "Amount debited successfully!";
    public static final String amtDebitedCode= "006";
    public static final String amtNotDebitedMessage= "Low Account Balance, requested amount could not be debited!";
    public static final String amtNotDebitedCode= "007";
    public static final String amtNotTranserferredMessage= "Account Balance in acct1 is insufficient, requested amount could not be debited!";
    public static final String amtNotTranserferredCode= "008";
    public static final String amtTranserferredMessage= "Transfer Request is successful!";
    public static final String amtTranserferredCode= "009";
}
