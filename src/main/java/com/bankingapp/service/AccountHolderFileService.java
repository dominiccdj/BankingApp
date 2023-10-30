package com.bankingapp.service;

import com.bankingapp.model.AccountHolder;

import java.io.IOException;

public interface AccountHolderFileService {
    void writeAccountHolderToFile(AccountHolder accountHolder) throws IOException;

    void deactivateAccountHolderInFile(AccountHolder accountHolder) throws IOException;
}
