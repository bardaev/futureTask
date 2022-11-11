package com.example.service.repository;

import com.example.service.entity.Amount;

public interface IRepositoryAmount {
    Amount getAmount(Long id);
    void addAmount(Long id, Long value);
}
