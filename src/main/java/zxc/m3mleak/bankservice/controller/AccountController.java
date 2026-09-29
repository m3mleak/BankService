package zxc.m3mleak.bankservice.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import zxc.m3mleak.bankservice.dto.AccountResponse;
import zxc.m3mleak.bankservice.dto.CreateAccountRequest;
import zxc.m3mleak.bankservice.dto.TransactionRequest;
import zxc.m3mleak.bankservice.model.Account;
import zxc.m3mleak.bankservice.model.Currency;
import zxc.m3mleak.bankservice.model.Money;
import zxc.m3mleak.bankservice.service.AccountService;
import zxc.m3mleak.bankservice.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;


    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<AccountResponse> getAllAccounts() {
        return accountService.getAllAccounts().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable int id) {
        return toResponse(accountService.getAccount(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Money initial = new Money(request.initBalance() != null ? request.initBalance() : BigDecimal.ZERO,
                Currency.valueOf(request.currency().toUpperCase()));
        Account account = accountService.createAccount(generateId(), request.name(), initial);

        return toResponse(account);
    }

    @PostMapping("/{id}/deposit")
    public AccountResponse deposit(@PathVariable int id, @Valid @RequestBody TransactionRequest request) {
        Money amount = new Money(request.amount(), Currency.valueOf(request.currency().toUpperCase()));
        transactionService.deposit(id, amount);
        return toResponse(accountService.getAccount(id));
    }

    @PostMapping("/{id}/withdraw")
    public AccountResponse withdraw(@PathVariable int id, @Valid @RequestBody TransactionRequest request) {
        Money amount = new Money(request.amount(), Currency.valueOf(request.currency().toUpperCase()));
        transactionService.withdraw(id, amount);
        return toResponse(accountService.getAccount(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable int id) {
        accountService.deleteAccount(id);
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getName(),
                account.getBalance().getAmount(),
                account.getBalance().getCurrency().name()
        );
    }

    private int generateId() {
        return (int) System.currentTimeMillis();
    }
}
