package com.example.demo.entity.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TransferAccount {
    private final Integer money;
    private final List<BookDAO> books;
}
