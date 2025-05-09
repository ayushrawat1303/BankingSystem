package com.banking.demo.repository;

import com.banking.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    public boolean existsByEmail(String email);
    public boolean existsByAccountNumber(String accountNumber);

    User findByAccountNumber(String accountNumber);
}
