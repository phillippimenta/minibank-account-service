package com.minibank.accountservice.domain.model;

import com.minibank.accountservice.domain.exception.AccountDomainException;
import com.minibank.accountservice.domain.utils.AccountNumberUtils;
import com.minibank.accountservice.domain.utils.ValidationUtils;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Account {

    private Long id;
    private String accountNumber;
    private final String holderName;
    private final BigDecimal balance;

    public static Account openNew(String holderName, BigDecimal initialBalance) {
        return new Account(holderName, initialBalance);
    }

    /**
     * Constructor for opening a new account
     */
    private Account(String holderName, BigDecimal balance) {
        this.holderName = ValidationUtils.requireNonBlank(holderName, "The holder name is required.");
        this.balance = requirePositiveInitialBalance(balance);
    }

    /**
     * Constructor for reconstitution (existing record).
     * Use this when loading an entity from the repository.
     */
    public Account(Long id, String accountNumber, String holderName, BigDecimal balance) {
        this.id = ValidationUtils.requireNonNull(id, "The account id cannot be null for existing records.");
        this.accountNumber = requireValidAccountNumber(accountNumber);
        this.holderName = ValidationUtils.requireNonBlank(holderName, "The holder name is required for existing records.");
        this.balance = ValidationUtils.requireNonNull(balance, "The balance cannot be null for existing records.");
    }

    private String requireValidAccountNumber(String accountNumber) {
        return AccountNumberUtils.formatAccountNumber(accountNumber);
    }

    private BigDecimal requirePositiveInitialBalance(BigDecimal initialBalance) {
        if (initialBalance == null) {
            throw new AccountDomainException("The initial balance is required.");
        }
        if (initialBalance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AccountDomainException("The initial balance must be greater than zero.");
        }
        return initialBalance;
    }
}
