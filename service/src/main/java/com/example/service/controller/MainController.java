package com.example.service.controller;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.example.service.aop.MetricAddRq;
import com.example.service.aop.MetricGetRq;
import com.example.service.aop.TotalRq;
import com.example.service.dto.AmountDto;
import com.example.service.dto.MessageDto;
import com.example.service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;

@RestController
@RequestMapping(path = "/")
public class MainController {

    private final AccountService accountService;

    @Autowired
    public MainController(AccountService accountService) {
        this.accountService = accountService;
    }

    @TotalRq
    @MetricGetRq
    @GetMapping(path = "/get/{id}")
    public ResponseEntity<AmountDto> getAmount(@PathVariable Long id) {
        System.out.println(id);
        return new ResponseEntity<>(accountService.getAmount(id), HttpStatus.OK);
    }

    @TotalRq
    @MetricAddRq
    @PostMapping(path = "/add")
    public ResponseEntity<AmountDto> addAmount(@RequestBody AmountDto amount) {
        return new ResponseEntity<>(accountService.addAmount(amount.getId(), amount.getValue()), HttpStatus.OK);
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<MessageDto> handleAddException(EntityExistsException e) {
        MessageDto addResponse = new MessageDto(e.getMessage());
        return new ResponseEntity<>(addResponse, HttpStatus.BAD_REQUEST);
    }
}
