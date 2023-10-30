package com.bankingapp.controller;

import com.bankingapp.model.AccountHolder;
import com.bankingapp.model.AccountHolderDTO;
import com.bankingapp.service.AccountHolderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("account-holder")
public class AccountHolderController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AccountHolderService accountHolderService;

    @GetMapping
    public List<AccountHolderDTO> getAllAccountHolders() {
        return accountHolderService.getAllAccountHolders()
                .stream()
                .map(accountHolder -> modelMapper.map(accountHolder, AccountHolderDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{oib}")
    public AccountHolderDTO getAccountHolderByOib(@PathVariable String oib) {
        return modelMapper.map(
                accountHolderService.getAccountHolderByOib(oib),
                AccountHolderDTO.class
        );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolderDTO createAccountHolder(@RequestBody @Validated AccountHolderDTO accountHolderDTO) {
        AccountHolder createdAccountHolder = accountHolderService.saveAccountHolder(modelMapper.map(accountHolderDTO, AccountHolder.class));
        return modelMapper.map(createdAccountHolder, AccountHolderDTO.class);
    }

    @DeleteMapping("/{oib}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccountHolderByOib(@PathVariable String oib) {
        accountHolderService.deleteAccountHolderByOib(oib);
    }

}
