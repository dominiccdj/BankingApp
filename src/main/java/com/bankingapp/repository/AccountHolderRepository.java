package com.bankingapp.repository;


import com.bankingapp.model.AccountHolder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Integer> {
    Optional<AccountHolder> findFirstByOib(String oib);

    @Transactional
    void deleteAccountHolderByOib(String oib);
}
