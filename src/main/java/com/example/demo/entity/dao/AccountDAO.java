package com.example.demo.entity.dao;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@ToString
@EqualsAndHashCode
public class AccountDAO {
    @Getter
    @Setter
    private Integer money;

    @Getter
    private final LinkedHashMap<String, BookDAO> books;

    public AccountDAO(Integer money) {
        this.money = money;
        this.books = new LinkedHashMap<>();
    }
}
