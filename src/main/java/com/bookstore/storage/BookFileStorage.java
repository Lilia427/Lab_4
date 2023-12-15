package com.bookstore.storage;

import com.bookstore.entity.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class BookFileStorage {

    private final File file;
    private final ObjectMapper objectMapper;
    private List<Book> books;

    public BookFileStorage(@Value("${bookstore.file-storage.path}") String filePath, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.file = new File(filePath);
        // Initialize books list
        this.books = readBooksFromFile();
    }

    private List<Book> readBooksFromFile() {
        if (file.exists() && file.length() > 0) {
            try {
                return objectMapper.readValue(file, new TypeReference<List<Book>>() {});
            } catch (IOException e) {
                throw new RuntimeException("Cannot read books from file", e);
            }
        } else {
            // Create a new file if it doesn't exist
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Failed to create new file", e);
            }
            return new CopyOnWriteArrayList<>();
        }
    }

    private void writeBooksToFile() {
        try {
            objectMapper.writeValue(file, books);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write books to file", e);
        }
    }

    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    public Optional<Book> findById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(generateId());
            books.add(book);
            writeBooksToFile();
        } else {
            books.replaceAll(b -> b.getId().equals(book.getId()) ? book : b);
        }
        return book;
    }

    private Long generateId() {
        Long maxId = books.stream()
                .mapToLong(Book::getId)
                .max()
                .orElse(0L);
        return maxId + 1;
    }

    public void deleteById(Long id) {
        books.removeIf(book -> book.getId().equals(id));
        writeBooksToFile();
    }
}
