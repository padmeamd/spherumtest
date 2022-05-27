package com.example.demo.entity.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDAO {
    private String author;
    private String name;
    private Integer price;
    private Integer amount;
}
