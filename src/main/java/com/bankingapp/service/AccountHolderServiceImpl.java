package com.bankingapp.service;

import com.bankingapp.exception.FileAlreadyExistsException;
import com.bankingapp.model.AccountHolder;
import com.bankingapp.model.AccountHolderStatus;
import com.bankingapp.repository.AccountHolderRepository;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountHolderServiceImpl implements AccountHolderService {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountHolderFileService accountHolderFileService;

    Logger logger = LoggerFactory.getLogger(AccountHolderServiceImpl.class);

    @Override
    public AccountHolder getActiveAccountHolderByOib(String oib) {
        return accountHolderRepository
                .findFirstByOibAndStatus(oib, AccountHolderStatus.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account holder with OIB:" + oib + " not found."));
    }

    @Override
    public List<AccountHolder> getAllActiveAccountHolders() {
        return accountHolderRepository.findAllByStatus(AccountHolderStatus.ACTIVE);
    }

    @Override
    public AccountHolder saveAccountHolder(AccountHolder accountHolder) {
        Optional<AccountHolder> possibleAccountHolder = accountHolderRepository.findFirstByOib(accountHolder.getOib());
        if (possibleAccountHolder.isEmpty()) {
            val result = accountHolderRepository.save(accountHolder);
            try {
                accountHolderFileService.writeAccountHolderToFile(result);
            } catch (IOException | FileAlreadyExistsException e) {
                accountHolderRepository.deleteAccountHolderByOib(accountHolder.getOib());
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "For Account holder with OIB:" + accountHolder.getOib() + " - Error creating their Account holder file. Account holder was not saved to the DB"
                );
            }
            logger.info("New " + result + " created");
            return result;
        } else if (possibleAccountHolder.get().getStatus().equals(AccountHolderStatus.INACTIVE)) {
            throw new ResponseStatusException(
                    HttpStatus.LOCKED,
                    "Account holder with OIB:" + accountHolder.getOib() + " already exists but it's deactivated for security reasons. Please contact administrator."
            );
        } else {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Account holder with OIB:" + accountHolder.getOib() + " already exists."
            );
        }
    }

    @Override
    public void deactivateAccountHolderByOib(String oib) {
        AccountHolder accountHolder = accountHolderRepository.findFirstByOib(oib)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account holder with OIB:" + oib + " not found."));

        accountHolder.setStatus(AccountHolderStatus.INACTIVE);
        try {
            accountHolderFileService.deactivateAccountHolderInFile(accountHolder);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "For Account holder with OIB:" + accountHolder.getOib() + " - Error creating their deactivated Account holder file." + e.getLocalizedMessage()
            );
        }
        accountHolderRepository.save(accountHolder);

    }
}
