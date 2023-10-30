package com.bankingapp.controller;

import com.bankingapp.model.AccountHolder;
import com.bankingapp.model.AccountHolderDTO;
import com.bankingapp.service.AccountHolderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AccountHolderControllerTest {

    @InjectMocks
    private AccountHolderController accountHolderController;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AccountHolderService accountHolderService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAccountHolders() {
        List<AccountHolder> accountHolders = Arrays.asList(new AccountHolder(), new AccountHolder());
        List<AccountHolderDTO> accountHolderDTOs = Arrays.asList(new AccountHolderDTO(), new AccountHolderDTO());

        when(accountHolderService.getAllActiveAccountHolders()).thenReturn(accountHolders);
        when(modelMapper.map(any(), any())).thenReturn(new AccountHolderDTO());

        List<AccountHolderDTO> result = accountHolderController.getAllAccountHolders();

        assertEquals(accountHolderDTOs.size(), result.size());
    }

    @Test
    public void testGetAccountHolderByOib() {
        String oib = "1234567890";
        AccountHolder accountHolder = new AccountHolder();
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO();

        when(accountHolderService.getActiveAccountHolderByOib(oib)).thenReturn(accountHolder);
        when(modelMapper.map(any(), any())).thenReturn(accountHolderDTO);

        AccountHolderDTO result = accountHolderController.getAccountHolderByOib(oib);

        assertEquals(accountHolderDTO, result);
    }

    @Test
    public void testCreateAccountHolder() {
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO();
        AccountHolder accountHolder = new AccountHolder();

        when(modelMapper.map(accountHolderDTO, AccountHolder.class)).thenReturn(accountHolder);
        when(accountHolderService.saveAccountHolder(accountHolder)).thenReturn(accountHolder);
        when(modelMapper.map(accountHolder, AccountHolderDTO.class)).thenReturn(accountHolderDTO);

        AccountHolderDTO result = accountHolderController.createAccountHolder(accountHolderDTO);

        assertEquals(accountHolderDTO, result);
    }

    @Test
    public void testDeleteAccountHolderByOib() {
        String oib = "1234567890";

        accountHolderController.deleteAccountHolderByOib(oib);

        Mockito.verify(accountHolderService).deactivateAccountHolderByOib(oib);
    }
}
