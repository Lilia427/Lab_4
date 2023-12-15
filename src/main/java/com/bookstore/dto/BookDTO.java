package com.bookstore.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookDTO {
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

