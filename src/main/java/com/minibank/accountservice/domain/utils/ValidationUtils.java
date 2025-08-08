package com.minibank.accountservice.domain.utils;

import com.minibank.accountservice.domain.exception.AccountDomainException;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static <T> T requireNonNull(T value, String message) {
        if (value == null) {
            throw new AccountDomainException(message);
        }
        return value;
    }

    public static String requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new AccountDomainException(message);
        }
        return value;
    }
}
