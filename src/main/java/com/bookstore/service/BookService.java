package com.bookstore.service;

import com.bookstore.dto.BookDTO;
import java.util.List;

public interface BookService {
    BookDTO findById(Long id);
    List<BookDTO> findAll();
    BookDTO save(BookDTO bookDTO);
    BookDTO update(Long id, BookDTO bookDTO);
    void deleteById(Long id);
}
