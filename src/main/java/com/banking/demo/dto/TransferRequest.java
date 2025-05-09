package com.banking.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TransferRequest {
    //acct1 is sending the amt to acct2
    private String accountNumber1;
    private BigDecimal transferAmt;
    private String accountNumber2;

}
