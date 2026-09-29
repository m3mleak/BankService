package zxc.m3mleak.bankservice.model;

import java.util.Objects;

public class Account {

    private final int id;
    private final String name;
    private Money balance;

    public Account(int id, String name, Money initBalance) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name");
        this.balance = Objects.requireNonNull(initBalance, "initBalance");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

   public Money getBalance() {
        return balance;
   }

   public void setBalance(Money balance) {
        this.balance = Objects.requireNonNull(balance, "balance");
   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Account{id=" + id + ", name='" + name + "', balance=" + balance + "}";
    }
}
