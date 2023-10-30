package com.bankingapp.service;

import com.bankingapp.model.AccountHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountHolderFileServiceImplTest {

    private AccountHolderFileServiceImpl accountHolderFileService;

    @BeforeEach
    public void setup() {
        accountHolderFileService = new AccountHolderFileServiceImpl();
    }

    @Test
    public void testWriteAccountHolderToFile() throws IOException {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setOib("1234567890");
        Path path = Paths.get(accountHolder.getOib());

        accountHolderFileService.writeAccountHolderToFile(accountHolder);

        String content = Files.readString(path);
        assertEquals(accountHolder.toString(), content);
        Files.delete(path);
    }

    @Test
    public void testDeactivateAccountHolderInFile() throws IOException {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setOib("1234567890");
        Path path = Paths.get(accountHolder.getOib() + "-DEACTIVATED");

        accountHolderFileService.deactivateAccountHolderInFile(accountHolder);

        String content = Files.readString(path);
        assertEquals(accountHolder.toString(), content);
        Files.delete(path);
    }

}
