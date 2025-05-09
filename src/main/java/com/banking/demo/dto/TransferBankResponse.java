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
public class TransferBankResponse {
    private String responseCode;
    private String responseMessage;
    private AccountInfo accountInfo1;
    private AccountInfo accountInfo2;

}
