package com.bookstore.service.impl;

import com.bookstore.dto.BookDTO;
import com.bookstore.entity.Book;
import com.bookstore.storage.BookFileStorage;
import com.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookFileStorage bookFileStorage;

    @Autowired
    public BookServiceImpl(BookFileStorage bookFileStorage) {
        this.bookFileStorage = bookFileStorage;
    }

    @Override
    public BookDTO findById(Long id) {
        return bookFileStorage.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<BookDTO> findAll() {
        return bookFileStorage.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO save(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        Book savedBook = bookFileStorage.save(book);
        return convertToDTO(savedBook);
    }

    @Override
    public BookDTO update(Long id, BookDTO bookDTO) {
        Book existingBook = bookFileStorage.findById(id).orElse(null);
        if (existingBook != null) {
            updateBookDetails(existingBook, bookDTO);
            bookFileStorage.save(existingBook);
            return convertToDTO(existingBook);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        bookFileStorage.deleteById(id);
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());
        dto.setDescription(book.getDescription());
        dto.setPublishDate(book.getPublishDate());
        dto.setGenre(book.getGenre());
        dto.setQuantityInStock(book.getQuantityInStock());
        return dto;
    }

    private Book convertToEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setDescription(dto.getDescription());
        book.setPublishDate(dto.getPublishDate());
        book.setGenre(dto.getGenre());
        book.setQuantityInStock(dto.getQuantityInStock());
        return book;
    }

    private void updateBookDetails(Book book, BookDTO dto) {
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
    }
}
