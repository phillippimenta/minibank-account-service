package com.minibank.accountservice.domain.model;

import com.minibank.accountservice.domain.exception.AccountDomainException;
import com.minibank.accountservice.domain.utils.AccountNumberUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    private final String holderName = "Phillip Pimenta";
    private final BigDecimal initialBalance = new BigDecimal("1000.00");
    private final Long accountId = 1L;
    private final String accountNumber = "123";

    @Test
    void givenValidAccount_whenCreatingNewAccount_thenAccountIsCreatedSuccessfully() {
        // When
        Account newAccount = Account.openNew(holderName, initialBalance);
        // Then
        assertNotNull(newAccount);
        assertEquals(holderName, newAccount.getHolderName());
        assertEquals(initialBalance, newAccount.getBalance());
    }

    @Test
    void givenAccountWithNullHolderName_whenCreatingNewAccount_thenDomainExceptionIsThrown() {
        // When
        AccountDomainException exception = assertThrows(AccountDomainException.class, () -> Account.openNew(null, initialBalance));
        // Then
        assertNotNull(exception);
        assertEquals("The holder name is required.", exception.getMessage());
    }

    @Test
    void givenAccountWithEmptyHolderName_whenCreatingNewAccount_thenDomainExceptionIsThrown() {
        // When
        AccountDomainException exception = assertThrows(AccountDomainException.class, () -> Account.openNew("", initialBalance));
        // Then
        assertNotNull(exception);
        assertEquals("The holder name is required.", exception.getMessage());
    }

    @Test
    void givenAccountWithNullInitialBalance_whenCreatingNewAccount_thenDomainExceptionIsThrown() {
        // When
        AccountDomainException exception = assertThrows(AccountDomainException.class, () -> Account.openNew(holderName, null));
        // Then
        assertNotNull(exception);
        assertEquals("The initial balance is required.", exception.getMessage());
    }

    @Test
    void givenAccountWithInitialBalanceNegative_whenCreatingNewAccount_thenDomainExceptionIsThrown() {
        // Given
        BigDecimal negativeBalance = new BigDecimal("-1");
        // When
        AccountDomainException exception = assertThrows(AccountDomainException.class, () -> Account.openNew(holderName, negativeBalance));
        // Then
        assertNotNull(exception);
        assertEquals("The initial balance must be greater than zero.", exception.getMessage());
    }

    @Test
    void givenValidData_whenConstructingExistingAccount_thenAccountIsCreatedSuccessfully() {
        // When
        Account account = new Account(accountId, accountNumber, holderName, initialBalance);
        // Then
        assertNotNull(account);
        assertEquals(accountId, account.getId());
        assertEquals(AccountNumberUtils.formatAccountNumber(accountNumber), account.getAccountNumber());
        assertEquals(holderName, account.getHolderName());
        assertEquals(initialBalance, account.getBalance());
    }

    @Test
    void givenNullId_whenConstructingExistingAccount_thenAccountDomainExceptionIsThrown() {
        // When
        AccountDomainException exception = assertThrows(AccountDomainException.class,
                () -> new Account(null, accountNumber, holderName, initialBalance));
        // Then
        assertNotNull(exception);
        assertEquals("The account id cannot be null for existing records.", exception.getMessage());
    }

    @Test
    void givenNullAccountNumber_whenConstructingExistingAccount_thenAccountDomainExceptionIsThrown() {
        // Given
        String nullAccountNumber = null;
        // When
        AccountDomainException exception = assertThrows(AccountDomainException.class,
                () -> new Account(accountId, nullAccountNumber, holderName, initialBalance));
        // Then
        assertNotNull(exception);
        assertEquals("The account number is required.", exception.getMessage());
    }

    @Test
    void givenBlankAccountNumber_whenConstructingExistingAccount_thenAccountDomainExceptionIsThrown() {
        // Given
        String emptyAccountNumber = "";
        // When
        AccountDomainException exception = assertThrows(AccountDomainException.class,
                () -> new Account(accountId, emptyAccountNumber, holderName, initialBalance));
        // Then
        assertNotNull(exception);
        assertEquals("The account number is required.", exception.getMessage());
    }

    @Test
    void givenAccountNumberNotNumeric_whenConstructingExistingAccount_thenAccountDomainExceptionIsThrown() {
        // When
        AccountDomainException exception = assertThrows(AccountDomainException.class,
                () -> new Account(accountId, "123A455BC", holderName, initialBalance));
        // Then
        assertNotNull(exception);
        assertEquals("The account number must be numeric.", exception.getMessage());
    }

    @Test
    void givenAccountNumberWithMoreThanEightDigits_whenConstructingExistingAccount_thenAccountDomainExceptionIsThrown() {
        // When
        AccountDomainException exception = assertThrows(AccountDomainException.class,
                () -> new Account(accountId, "123456789", holderName, initialBalance));
        // Then
        assertNotNull(exception);
        assertEquals("The account number must have at most 8 digits.", exception.getMessage());
    }
}
