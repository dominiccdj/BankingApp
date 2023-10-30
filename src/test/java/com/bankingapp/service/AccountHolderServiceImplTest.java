package com.bankingapp.service;

import com.bankingapp.model.AccountHolder;
import com.bankingapp.model.AccountHolderStatus;
import com.bankingapp.repository.AccountHolderRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountHolderServiceImplTest {

    @InjectMocks
    private AccountHolderServiceImpl accountHolderService;

    @Mock
    private AccountHolderRepository accountHolderRepository;

    @Mock
    private AccountHolderFileService accountHolderFileService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetActiveAccountHolderByOib() {
        String oib = "1234567890";
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setOib(oib);
        accountHolder.setStatus(AccountHolderStatus.ACTIVE);

        when(accountHolderRepository.findFirstByOibAndStatus(oib, AccountHolderStatus.ACTIVE)).thenReturn(Optional.of(accountHolder));

        AccountHolder result = accountHolderService.getActiveAccountHolderByOib(oib);

        assertEquals(accountHolder, result);
    }

    @Test
    public void testGetActiveAccountHolderByOibNotFound() {
        String oib = "1234567890";

        when(accountHolderRepository.findFirstByOibAndStatus(oib, AccountHolderStatus.ACTIVE)).thenReturn(Optional.empty());

        assertThrowsResponseStatusException(() -> accountHolderService.getActiveAccountHolderByOib(oib), "Account holder with OIB:" + oib + " not found.");
    }

    @Test
    public void testGetAllActiveAccountHolders() {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setStatus(AccountHolderStatus.ACTIVE);

        when(accountHolderRepository.findAllByStatus(AccountHolderStatus.ACTIVE)).thenReturn(Collections.singletonList(accountHolder));

        assertEquals(Collections.singletonList(accountHolder), accountHolderService.getAllActiveAccountHolders());
    }

    @SneakyThrows
    @Test
    public void testSaveAccountHolder() {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setOib("1234567890");
        when(accountHolderRepository.findFirstByOib(accountHolder.getOib())).thenReturn(Optional.empty());
        when(accountHolderRepository.save(any())).thenReturn(accountHolder);

        AccountHolder savedAccountHolder = accountHolderService.saveAccountHolder(accountHolder);

        assertEquals(accountHolder, savedAccountHolder);
        verify(accountHolderRepository).save(accountHolder);
        verify(accountHolderFileService).writeAccountHolderToFile(accountHolder);
    }

    @Test
    public void testSaveAccountHolderExistingInactive() {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setOib("1234567890");
        accountHolder.setStatus(AccountHolderStatus.INACTIVE);

        when(accountHolderRepository.findFirstByOib(accountHolder.getOib())).thenReturn(Optional.of(accountHolder));

        assertThrowsResponseStatusException(() -> accountHolderService.saveAccountHolder(accountHolder), "Account holder with OIB:" + accountHolder.getOib() + " already exists but it's deactivated for security reasons. Please contact administrator.");
    }

    @Test
    public void testSaveAccountHolderExistingActive() {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setOib("1234567890");
        accountHolder.setStatus(AccountHolderStatus.ACTIVE);

        when(accountHolderRepository.findFirstByOib(accountHolder.getOib())).thenReturn(Optional.of(accountHolder));

        assertThrowsResponseStatusException(() -> accountHolderService.saveAccountHolder(accountHolder), "Account holder with OIB:" + accountHolder.getOib() + " already exists.");
    }

    @Test
    public void testSaveAccountHolderFileException() throws IOException {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setOib("1234567890");

        when(accountHolderRepository.findFirstByOib(accountHolder.getOib())).thenReturn(Optional.empty());
        doThrow(IOException.class).when(accountHolderFileService).writeAccountHolderToFile(accountHolder);

        assertThrowsResponseStatusException(() -> accountHolderService.saveAccountHolder(accountHolder), "For Account holder with OIB:" + accountHolder.getOib() + " - Error creating their Account holder file. Account holder was not saved to the DB");
    }

    @SneakyThrows
    @Test
    public void testDeactivateAccountHolderByOib() {
        String oib = "1234567890";
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setOib(oib);

        when(accountHolderRepository.findFirstByOib(oib)).thenReturn(Optional.of(accountHolder));

        accountHolderService.deactivateAccountHolderByOib(oib);

        verify(accountHolderFileService).deactivateAccountHolderInFile(accountHolder);
        verify(accountHolderRepository).save(accountHolder);
        assertEquals(AccountHolderStatus.INACTIVE, accountHolder.getStatus());
    }

    @Test
    public void testDeactivateAccountHolderByOibNotFound() {
        String oib = "1234567890";

        when(accountHolderRepository.findFirstByOib(oib)).thenReturn(Optional.empty());

        assertThrowsResponseStatusException(() -> accountHolderService.deactivateAccountHolderByOib(oib), "Account holder with OIB:" + oib + " not found.");
    }
    
    private void assertThrowsResponseStatusException(Runnable runnable, String expectedMessage) {
        try {
            runnable.run();
        } catch (ResponseStatusException e) {
            assertEquals(expectedMessage, e.getReason());
        }
    }
}
