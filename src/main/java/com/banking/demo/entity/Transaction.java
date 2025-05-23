package com.banking.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "transactions_rec")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber1;
    private BigDecimal accountBalance1;
    private String accountNumber2;
    private BigDecimal accountBalance2;
    private String status;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
