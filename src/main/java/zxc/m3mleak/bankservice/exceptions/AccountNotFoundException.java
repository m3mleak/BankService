package zxc.m3mleak.bankservice.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(int id) {
        super("Account not found: id=" + id);
    }
}
