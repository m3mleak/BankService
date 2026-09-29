package zxc.m3mleak.bankservice.repository;

import org.springframework.stereotype.Repository;
import zxc.m3mleak.bankservice.model.Account;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountRepository {

    private final ConcurrentHashMap<Integer, Account> accounts = new ConcurrentHashMap<>();

    public void save(Account account) {
        accounts.put(account.getId(), account);
    }

    public Optional<Account> findById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean deleteById(int id) {
        return accounts.remove(id) != null;
    }

    public Collection<Account> findAll() {
        return List.copyOf(accounts.values());
    }

    public int size() {
        return accounts.size();
    }

}
