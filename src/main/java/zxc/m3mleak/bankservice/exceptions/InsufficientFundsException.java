package zxc.m3mleak.bankservice.exceptions;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(int accountId) {
        super("Insufficient funds on account id=" + accountId);
    }
}
