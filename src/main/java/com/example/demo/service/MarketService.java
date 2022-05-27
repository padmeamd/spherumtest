package com.example.demo.service;


import com.example.demo.entity.dao.BookDAO;
import com.example.demo.entity.dao.TransferAccount;
import com.example.demo.entity.dto.*;
import com.example.demo.repo.MarketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MarketService {

    private final MarketRepository marketRepository;

    @Autowired
    public MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }


    public ProductListDTO getProducts() {
        return mapProducts(marketRepository.getBooks());
    }

    public AccountDTO getAccountInfo() {
        return mapAccount(marketRepository.getAccount());
    }

    public void makeDeal(Integer bookId, Integer amount) {
        marketRepository.makeDeal(bookId, amount);
    }

    private ProductListDTO mapProducts(List<BookDAO> data) {
        List<ProductEntryDTO> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            BookDAO b = data.get(i);
            list.add(
                    new ProductEntryDTO(
                            i,
                            new BookDTO(b.getName(), b.getAuthor()), b.getPrice(), b.getAmount()
                    )
            );
        }
        return new ProductListDTO(list);
    }


    private AccountDTO mapAccount(TransferAccount account) {
        List<BookEntryDTO> list = account.getBooks().stream().map(t ->
                new BookEntryDTO(new BookDTO(t.getName(), t.getAuthor()), t.getAmount()))
                .collect(Collectors.toList());
        return new AccountDTO(account.getMoney(), list);
    }


}
