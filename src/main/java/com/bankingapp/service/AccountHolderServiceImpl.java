package com.bankingapp.service;

import com.bankingapp.model.AccountHolder;
import com.bankingapp.repository.AccountHolderRepository;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountHolderServiceImpl implements AccountHolderService {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    Logger logger = LoggerFactory.getLogger(AccountHolderServiceImpl.class);

    @Override
    public AccountHolder getAccountHolderByOib(String oib) {
        return accountHolderRepository
                .findFirstByOib(oib)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account holder with OIB:" + oib + " not found."));
    }

    @Override
    public List<AccountHolder> getAllAccountHolders() {
        return accountHolderRepository.findAll();
    }

    @Override
    public AccountHolder saveAccountHolder(AccountHolder accountHolder) {
        if (accountHolderRepository.findFirstByOib(accountHolder.getOib()).isEmpty()) {
            val result = accountHolderRepository.save(accountHolder);
            logger.info("New " + result + " created");
            return result;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Account holder with OIB:" + accountHolder.getOib() + " already exists."
            );
        }
    }

    @Override
    public void deleteAccountHolderByOib(String oib) {
        if (accountHolderRepository.findFirstByOib(oib).isPresent()) {
            accountHolderRepository.deleteAccountHolderByOib(oib);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account holder with OIB:" + oib + " not found."
            );
        }
    }
}
