package com.bankingapp.repository;


import com.bankingapp.model.AccountHolder;
import com.bankingapp.model.AccountHolderStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Integer> {
    Optional<AccountHolder> findFirstByOibAndStatus(String oib, AccountHolderStatus status);

    Optional<AccountHolder> findFirstByOib(String oib);

    List<AccountHolder> findAllByStatus(AccountHolderStatus status);

    @Transactional
    void deleteAccountHolderByOib(String oib);
}
