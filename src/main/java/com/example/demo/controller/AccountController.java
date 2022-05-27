package com.example.demo.controller;

import com.example.demo.entity.dto.AccountDTO;
import com.example.demo.service.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AccountController {

    private final MarketService service;

    @Autowired
    public AccountController(MarketService service) {
        this.service = service;
    }


    @GetMapping("/account")
    public AccountDTO getAccountInfo() {
        return service.getAccountInfo();
    }


}
