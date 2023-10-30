package com.bankingapp.service;

import com.bankingapp.model.AccountHolder;

import java.util.List;

public interface AccountHolderService {

    AccountHolder getAccountHolderByOib(String oib);

    List<AccountHolder> getAllAccountHolders();

    AccountHolder saveAccountHolder(AccountHolder accountHolder);

    void deleteAccountHolderByOib(String oib);
}
