package com.example.service.service;

import com.example.service.dto.AmountDto;

public interface AccountService {
    /**
     * Retrieves current balance or zero if addAmount() method was not called before for specified id
     *
     * @param id balance identifier
     */
    AmountDto getAmount(Long id);

    /**
     * Increases balance or set if addAmount() method was called first time
     *
     * @param id balance identifier
     * @param value positive or negative value, which must be added to current balance
     */
    AmountDto addAmount(Long id, Long value);
}
