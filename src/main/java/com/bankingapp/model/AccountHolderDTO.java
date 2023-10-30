package com.bankingapp.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountHolderDTO {
    @NotEmpty
    @Size(min = 2, message = "First name should have at least 2 characters")
    private String firstName;
    @NotEmpty
    @Size(min = 2, message = "Last name should have at least 2 characters")
    private String lastName;
    @NotEmpty
    @Size(min = 11, max = 11, message = "OIB should have exactly 11 characters")
    private String oib;
    private AccountHolderStatus status = AccountHolderStatus.ACTIVE;
}
