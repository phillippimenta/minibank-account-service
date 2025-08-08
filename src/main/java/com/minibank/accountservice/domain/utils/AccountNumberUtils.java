package com.minibank.accountservice.domain.utils;

import com.minibank.accountservice.domain.exception.AccountDomainException;

public class AccountNumberUtils {

    private AccountNumberUtils() {
    }

    public static String formatAccountNumber(String accountNumber) {
        String accountNumberNormalized = AccountNumberUtils.normalizeAccountNumber(accountNumber);
        if (accountNumberNormalized.length() > 8) {
            throw new AccountDomainException("The account number must have at most 8 digits.");
        }
        return String.format("%8s", accountNumberNormalized).replace(' ', '0');
    }

    public static String normalizeAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new AccountDomainException("The account number is required.");
        }
        String accountNumberNormalized = accountNumber.trim();
        if (!accountNumberNormalized.matches("\\d+")) {
            throw new AccountDomainException("The account number must be numeric.");
        }
        return accountNumberNormalized;
    }
}
