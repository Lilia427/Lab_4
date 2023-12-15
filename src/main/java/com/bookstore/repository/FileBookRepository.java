package com.bookstore.repository;

import com.bookstore.entity.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FileBookRepository {

    private final File file;
    private final ObjectMapper objectMapper;

    public FileBookRepository(String filePath, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.file = new File(filePath);
    }

    public List<Book> findAll() {
        try {
            if (file.exists() && file.length() > 0) {
                return objectMapper.readValue(file, new TypeReference<List<Book>>() {});
            }
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Error reading books from file", e);
        }
    }

    public Optional<Book> findById(Long id) {
        return findAll().stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    public Book save(Book book) {
        List<Book> books = findAll();
        if (book.getId() == null) {
            book.setId(generateId(books));
        } else {
            books.removeIf(b -> b.getId().equals(book.getId()));
        }
        books.add(book);
        writeBooksToFile(books);
        return book;
    }

    public void deleteById(Long id) {
        List<Book> books = findAll();
        books.removeIf(book -> book.getId().equals(id));
        writeBooksToFile(books);
    }

    public List<Book> findByTitle(String title) {
        return findAll().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    public List<Book> findByAuthor(String author) {
        return findAll().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Book> findByGenre(String genre) {
        return findAll().stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    private Long generateId(List<Book> books) {
        Long maxId = books.stream().mapToLong(Book::getId).max().orElse(0L);
        return maxId + 1;
    }

    private void writeBooksToFile(List<Book> books) {
        try {
            objectMapper.writeValue(file, books);
        } catch (IOException e) {
            throw new RuntimeException("Error writing books to file", e);
        }
    }
}
