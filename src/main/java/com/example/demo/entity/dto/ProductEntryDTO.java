package com.example.demo.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntryDTO {
    private Integer id;
    private BookDTO book;
    private Integer price;
    private Integer amount;
}
