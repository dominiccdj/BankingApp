package com.bankingapp.service;

import com.bankingapp.model.AccountHolder;

import java.util.List;

public interface AccountHolderService {

    AccountHolder getActiveAccountHolderByOib(String oib);

    List<AccountHolder> getAllActiveAccountHolders();

    AccountHolder saveAccountHolder(AccountHolder accountHolder);

    void deactivateAccountHolderByOib(String oib);
}
