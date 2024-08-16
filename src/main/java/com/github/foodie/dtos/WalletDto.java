package com.github.foodie.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {
    private UUID id;
    private UserDto userDto;
    private Long userId;
    private BigDecimal amount;
    private List<WalletTransactionDto> walletTransactionDtos;
    private Instant createdOn;
    private Instant updatedOn;

}
