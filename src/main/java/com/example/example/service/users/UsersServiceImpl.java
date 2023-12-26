package com.example.example.service.users;

import com.example.example.model.Pokemon;
import com.example.example.model.User;
import com.example.example.repository.model.PokemonEntity;
import com.example.example.repository.model.UserEntity;
import com.example.example.repository.users.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
        log.info("Fetching all users");
        return convertToUserList((List<UserEntity>) usersRepository.findAll());
    }


    @Override
    public Optional<User> getUserById(Long id) {
        log.debug("Fetching user by ID: {}", id);
        return usersRepository.findById(id).map(this::convertToUser);
    }

    @Override
    public User createUser(User user) {
        UserEntity userEntity = convertToUserEntity(user);
        UserEntity savedEntity = usersRepository.save(userEntity);
        return convertToUser(savedEntity);
    }

    @Override
    public Optional<Pokemon> addPokemonToUser(Long userId, Pokemon pokemon) {
        Optional<User> existingUser = getUserById(userId);
        if (existingUser.isPresent()) {
            UserEntity userEntity = convertToUserEntity(existingUser.get());

            PokemonEntity pokemonEntity = PokemonEntity.builder()
                    .name(pokemon.getName())
                    .type(pokemon.getType())
                    .imgUrl(pokemon.getImgUrl())
                    .build();

            userEntity.getPokemonList().add(pokemonEntity);
            UserEntity savedUserEntity = usersRepository.save(userEntity);

            return Optional.ofNullable(convertToPokemon(savedUserEntity.getPokemonList().stream()
                    .findFirst()
                    .orElse(null)));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
    }

    @Override
    public Optional<Pokemon> updatePokemonForUser(Long userId, Long pokemonId, Pokemon updatedPokemon) {
        Optional<UserEntity> existingUser = usersRepository.findById(userId);
        if (existingUser.isPresent()) {
            UserEntity userEntity = existingUser.get();

            Optional<PokemonEntity> existingPokemonEntity = userEntity.getPokemonList().stream()
                    .findFirst();

            if (existingPokemonEntity.isPresent()) {
                PokemonEntity pokemonEntityToUpdate = existingPokemonEntity.get();
                pokemonEntityToUpdate.setName(updatedPokemon.getName());
                pokemonEntityToUpdate.setType(updatedPokemon.getType());

                UserEntity savedUserEntity = usersRepository.save(userEntity);

                return Optional.ofNullable(convertToPokemon(savedUserEntity.getPokemonList().stream()
                        .findFirst()
                        .orElse(null)));
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pokemon not found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
    }

    @Override
    public Optional<User> updateUser(User user) {
        Optional<User> existingUser = getUserById(user.getId());
        if (existingUser.isPresent()) {
            UserEntity updatedUserEntity = convertToUserEntity(user);
            UserEntity savedEntity = usersRepository.save(updatedUserEntity);
            return Optional.ofNullable(convertToUser(savedEntity));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }


    @Override
    public void deleteUser(Long userId) {
        log.warn("Deleting user with ID: {}", userId);
        usersRepository.deleteById(userId);
    }


    /**
     * @param userEntity
     * @return Converts a userEntity to a user
     */
    private User convertToUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        List<Pokemon> pokemonList = convertToPokemonList(new ArrayList<>(userEntity.getPokemonList()));

        return User.builder()
                .id(userEntity.getId())
                .firstname(userEntity.getFirstname())
                .lastname(userEntity.getLastname())
                .pokemonList(pokemonList)
                .build();
    }

    /**
     * @param user
     * @return Converts a user to a userEntity.
     */
    private UserEntity convertToUserEntity(User user) {
        if (user == null) {
            return null;
        }

        return UserEntity.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .pokemonList(convertToPokemonEntityList(user.getPokemonList(), user.getId()))
                .build();
    }

    /**
     * @param userEntities
     * @return Converts an List of userEntity to a list of user
     */
    private List<User> convertToUserList(List<UserEntity> userEntities) {
        return StreamSupport.stream(userEntities.spliterator(), false)
                .map(this::convertToUser)
                .collect(Collectors.toList());
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
     * @param pokemonList
     * @param userId
     * @return Converts a list of pokemon to a list of pokemonEntity
     */
    private List<PokemonEntity> convertToPokemonEntityList(List<Pokemon> pokemonList, Long userId) {
        if (pokemonList != null) {
            return pokemonList.stream()
                    .map(pokemon -> PokemonEntity.builder()
                            .id(pokemon.getId())
                            .name(pokemon.getName())
                            .type(pokemon.getType())
                            .imgUrl(pokemon.getImgUrl())
                            .build())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * @param pokemonEntity
     * @return Converts a pokemonEntity to a pokemon
     */
    private Pokemon convertToPokemon(PokemonEntity pokemonEntity) {
        if (pokemonEntity == null) {
            return null;
        }

        return Pokemon.builder()
                .id(pokemonEntity.getId())
                .name(pokemonEntity.getName())
                .type(pokemonEntity.getType())
                .build();
    }
}