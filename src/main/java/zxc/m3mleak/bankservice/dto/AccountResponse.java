package zxc.m3mleak.bankservice.dto;

import java.math.BigDecimal;

public record AccountResponse(
        int id,
        String name,
        BigDecimal balance,
        String currency
) {
}
