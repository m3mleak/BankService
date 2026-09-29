package zxc.m3mleak.bankservice.service;

import org.springframework.stereotype.Service;
import zxc.m3mleak.bankservice.exceptions.AccountNotFoundException;
import zxc.m3mleak.bankservice.exceptions.InsufficientFundsException;
import zxc.m3mleak.bankservice.model.Account;
import zxc.m3mleak.bankservice.model.Money;
import zxc.m3mleak.bankservice.repository.AccountRepository;

import java.util.Objects;

@Service
public class TransactionService {

    private final AccountRepository repository;

    public TransactionService(AccountRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    public Money getBalance(int accountId) {
        return findAccount(accountId).getBalance();
    }

    public synchronized void withdraw(int accountId, Money amount) {
        requirePositive(amount);
        Account account = findAccount(accountId);

        Money current = account.getBalance();
        if (current.isLessThan(amount)) {
            throw new InsufficientFundsException(accountId);
        }
        account.setBalance(current.subtract(amount));
    }

    public synchronized void deposit(int accountId, Money amount) {
        requirePositive(amount);
        Account account = findAccount(accountId);
        account.setBalance(account.getBalance().add(amount));
    }

    private Account findAccount(int id) {
        return repository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    private void requirePositive(Money amount) {
        Objects.requireNonNull(amount, "amount");
        if (amount.isNegative() || amount.getAmount().signum() == 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}
