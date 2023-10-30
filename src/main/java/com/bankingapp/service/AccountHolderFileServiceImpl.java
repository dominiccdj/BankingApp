package com.bankingapp.service;

import com.bankingapp.model.AccountHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AccountHolderFileServiceImpl implements AccountHolderFileService {
    @Override
    public void writeAccountHolderToFile(AccountHolder accountHolder) throws IOException {
        deleteIfAnyPathExists(accountHolder);
        Path path = Paths.get(accountHolder.getOib());
        String str = accountHolder.toString();
        writeToPath(str, path);
    }

    @Override
    public void deactivateAccountHolderInFile(AccountHolder accountHolder) throws IOException {
        deleteIfAnyPathExists(accountHolder);
        Path path = Paths.get(accountHolder.getOib() + "-DEACTIVATED");
        String str = accountHolder.toString();
        writeToPath(str, path);
    }

    private void writeToPath(String string, Path path) throws IOException {
        byte[] strToBytes = string.getBytes();
        Files.write(path, strToBytes);
    }

    private void deleteIfAnyPathExists(AccountHolder accountHolder) throws IOException {
        Path path = Paths.get(accountHolder.getOib());
        Path pathDeactivated = Paths.get(accountHolder.getOib() + "-DEACTIVATED");

        if (Files.exists(path)) {
            Files.delete(path);
        }
        if (Files.exists(pathDeactivated)) {
            Files.delete(pathDeactivated);
        }
    }
}
