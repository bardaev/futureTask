package com.example.service.service;

import com.example.service.dto.AmountDto;
import com.example.service.entity.Amount;
import com.example.service.repository.IRepositoryAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final IRepositoryAmount repositoryAmount;

    @Autowired
    public AccountServiceImpl(IRepositoryAmount repositoryAmount) {
        this.repositoryAmount = repositoryAmount;
    }

    @Override
    public AmountDto getAmount(Long id) {
        Amount amount = repositoryAmount.getAmount(id);
        if (amount == null) {
            return new AmountDto(0L, 0L);
        }
        return new AmountDto(amount.getId(), amount.getValue());
    }

    @Override
    public AmountDto addAmount(Long id, Long value) {
        repositoryAmount.addAmount(id, value);
        return new AmountDto(id, value);
    }
}
