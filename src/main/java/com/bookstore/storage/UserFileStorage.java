package com.bookstore.storage;

import com.bookstore.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class UserFileStorage {

    private final File file;
    private final ObjectMapper objectMapper;
    private List<User> users;

    public UserFileStorage(@Value("C:\\lab_4\\User") String filePath, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.file = new File(filePath);
        this.users = readUsersFromFile();
    }

    private List<User> readUsersFromFile() {
        if (file.exists() && file.length() > 0) {
            try {
                return objectMapper.readValue(file, new TypeReference<List<User>>() {});
            } catch (IOException e) {
                throw new RuntimeException("Cannot read users from file", e);
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Failed to create new file for users", e);
            }
            return new CopyOnWriteArrayList<>();
        }
    }

    private void writeUsersToFile() {
        try {
            objectMapper.writeValue(file, users);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write users to file", e);
        }
    }

    public List<User> findAll() {
        return new CopyOnWriteArrayList<>(users);
    }

    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(generateId());
            users.add(user);
        } else {
            users.replaceAll(b -> b.getId().equals(user.getId()) ? user : b);
        }
        writeUsersToFile();
        return user;
    }

    private Long generateId() {
        Long maxId = users.stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0L);
        return maxId + 1;
    }

    public void deleteById(Long id) {
        users.removeIf(user -> user.getId().equals(id));
        writeUsersToFile();
    }
}
