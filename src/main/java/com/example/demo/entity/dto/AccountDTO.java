package com.example.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountDTO {
    private Integer balance;
    private final List<BookEntryDTO> books;
}
