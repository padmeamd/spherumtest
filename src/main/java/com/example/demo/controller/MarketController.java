package com.example.demo.controller;


import com.example.demo.entity.DealEntry;
import com.example.demo.entity.dto.ProductListDTO;
import com.example.demo.service.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MarketController {

    private final MarketService service;

    @Autowired
    public MarketController(MarketService service) {
        this.service = service;
    }

    @PostMapping("/market/deal")
    public void makeDeal(@RequestBody DealEntry entry) {
        log.info("Request with: " + entry);
        service.makeDeal(entry.getId(), entry.getAmount());
    }

    @GetMapping("/market")
    public ProductListDTO getProducts() {
        return service.getProducts();
    }
}
