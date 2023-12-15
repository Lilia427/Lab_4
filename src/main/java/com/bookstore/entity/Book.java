package com.bookstore.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
    private String description;
    private LocalDate publishDate;
    private String genre;
    private Integer quantityInStock;
}
