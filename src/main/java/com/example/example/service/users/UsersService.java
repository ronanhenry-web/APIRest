package com.example.example.service.users;

import com.example.example.model.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    /**
     * @return All users
     */
    List<User> getAllUsers();

    /**
     * @param id
     * @return User by id
     */
    Optional<User> getUserById(Long id);
}
