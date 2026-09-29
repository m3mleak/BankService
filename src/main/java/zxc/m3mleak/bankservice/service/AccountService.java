package zxc.m3mleak.bankservice.service;

import org.springframework.stereotype.Service;
import zxc.m3mleak.bankservice.exceptions.AccountNotFoundException;
import zxc.m3mleak.bankservice.model.Account;
import zxc.m3mleak.bankservice.model.Money;
import zxc.m3mleak.bankservice.repository.AccountRepository;

import java.util.Collection;
import java.util.Objects;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    public Account createAccount(int id, String name, Money initBalance) {
        if (repository.findById(id).isPresent()) {
            throw new IllegalStateException("Account already exists: id=" + id);
        }

        Account account = new Account(id, name, initBalance);
        repository.save(account);
        return account;
    }

    public Account getAccount(int id) {
        return repository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    public Collection<Account> getAllAccounts() {
        return repository.findAll();
    }

    public void deleteAccount(int id) {
        if (!repository.deleteById(id)) {
            throw new AccountNotFoundException(id);
        }
    }
}
