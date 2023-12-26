package com.example.example.service.users;

import com.example.example.model.Pokemon;
import com.example.example.model.User;
import com.example.example.repository.model.PokemonEntity;
import com.example.example.repository.model.UserEntity;
import com.example.example.repository.users.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return convertToUserList(usersRepository.findAll());
    }


    @Override
    public Optional<User> getUserById(Long id) {
        return usersRepository.findById(id).map(this::convertToUser);
    }


    /**
     * @param userEntity
     * @return Converts a userEntity to a user
     */
    private User convertToUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return User.builder()
                .id(userEntity.getId())
                .firstname(userEntity.getFirstname())
                .lastname(userEntity.getLastname())
                .pokemonList(convertToPokemonList(userEntity.getPokemonList()))
                .build();
    }

    /**
     * @param pokemonEntities
     * @return Converts a list of pokemonEntity to a list of pokemon
     */
    private List<Pokemon> convertToPokemonList(List<PokemonEntity> pokemonEntities) {
        if (pokemonEntities != null) {
            return pokemonEntities.stream()
                    .map(pokemonEntity -> Pokemon.builder()
                            .id(pokemonEntity.getId())
                            .name(pokemonEntity.getName())
                            .type(pokemonEntity.getType())
                            .imgUrl(pokemonEntity.getImgUrl())
                            .build())
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * @param userEntities
     * @return Converts an List of userEntity to a list of user
     */
    private List<User> convertToUserList(Iterable<UserEntity> userEntities) {
        return StreamSupport.stream(userEntities.spliterator(), false)
                .map(this::convertToUser)
                .collect(Collectors.toList());
    }
}