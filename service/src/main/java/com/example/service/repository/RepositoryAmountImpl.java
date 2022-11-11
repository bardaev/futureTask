package com.example.service.repository;

import com.example.service.entity.Amount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class RepositoryAmountImpl implements IRepositoryAmount{

    private final EntityManager em;

    @Autowired
    public RepositoryAmountImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Amount getAmount(Long id) {
        return em.find(Amount.class, id);
    }

    @Override
    public void addAmount(Long id, Long value) {
        Amount amount = em.find(Amount.class, id);
        if (amount == null) {
            Amount newAmount = new Amount();
            newAmount.setId(id);
            newAmount.setValue(value);
            em.persist(newAmount);
        } else {
            amount.setValue(amount.getValue() + value);
        }
    }
}
