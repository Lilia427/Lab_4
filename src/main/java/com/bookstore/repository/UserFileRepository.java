package com.bookstore.repository;

import com.bookstore.entity.User;
import com.bookstore.storage.UserFileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserFileRepository {

    private final UserFileStorage userFileStorage;

    @Autowired
    public UserFileRepository(UserFileStorage userFileStorage) {
        this.userFileStorage = userFileStorage;
    }

    public Optional<User> findById(Long id) {
        return userFileStorage.findById(id);
    }

    public List<User> findAll() {
        return userFileStorage.findAll();
    }

    public User save(User user) {
        return userFileStorage.save(user);
    }

    public void deleteById(Long id) {
        userFileStorage.deleteById(id);
    }

}
