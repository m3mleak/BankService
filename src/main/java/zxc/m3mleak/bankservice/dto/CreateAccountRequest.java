package zxc.m3mleak.bankservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotBlank(message = "Owner name is required")
        String name,

        @PositiveOrZero(message = "Initial balance cannot be negative")
        BigDecimal initBalance,

        @NotBlank(message = "Currency is required")
        String currency
) {
}
