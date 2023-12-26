package com.example.example.service.users;

import com.example.example.model.Pokemon;
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

    /**
     * @param user
     * @return Create user
     */
    User createUser(User user);

    /**
     * @param userId
     * @param pokemon
     * @return Add pokemon to user
     */
    Optional<Pokemon> addPokemonToUser(Long userId, Pokemon pokemon);

    /**
     * @param userId
     * @param pokemonId
     * @param updatedPokemon
     * @return Update pokemon for user
     */
    Optional<Pokemon> updatePokemonForUser(Long userId, Long pokemonId, Pokemon updatedPokemon);

    /**
     * @param user
     * @return Update User
     */
    Optional<User> updateUser(User user);

    /**
     * @param userId
     */
    void deleteUser(Long userId);
}
