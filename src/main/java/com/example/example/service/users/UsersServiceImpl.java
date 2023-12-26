package com.example.example.service.users;

import com.example.example.model.Pokemon;
import com.example.example.model.User;
import com.example.example.repository.model.PokemonEntity;
import com.example.example.repository.model.UserEntity;
import com.example.example.repository.pokemons.PokemonsRepository;
import com.example.example.repository.users.UsersRepository;
import com.example.example.utils.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final PokemonsRepository pokemonsRepository;

    private final UserMapper userMapper;

    public UsersServiceImpl(UsersRepository usersRepository, PokemonsRepository pokemonsRepository, UserMapper userMapper) {
        this.usersRepository = usersRepository;
        this.pokemonsRepository = pokemonsRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAllUsers() {
//        log.info("Fetching all users");
        List<UserEntity> userEntities = (List<UserEntity>) usersRepository.findAll();
        return userMapper.userEntitiesToUsers(userEntities);
    }


    @Override
    public Optional<User> getUserById(Long id) {
//        log.debug("Fetching user by ID: {}", id);
        return Optional.ofNullable(userMapper.userEntityToUser(usersRepository.findById(id).orElse(null)));
    }

    @Override
    public User createUser(User user) {
        UserEntity userEntity = userMapper.userToUserEntity(user);
        UserEntity savedEntity = usersRepository.save(userEntity);
        return userMapper.userEntityToUser(savedEntity);
    }

    @Override
    public Optional<Pokemon> addPokemonToUser(Long userId, Pokemon pokemon) {
        Optional<User> userOptional = getUserById(userId);
        if (userOptional.isPresent() && userOptional.get().getPokemonList().size() < 6) {
            UserEntity userEntity = userMapper.userToUserEntity(userOptional.get());
            PokemonEntity pokemonEntity = PokemonEntity.builder()
                    .name(pokemon.getName())
                    .type(pokemon.getType())
                    .imgUrl(pokemon.getImgUrl())
                    .user(userEntity)
                    .build();
            PokemonEntity savedPokemonEntity = pokemonsRepository.save(pokemonEntity);
            return Optional.of(userMapper.pokemonEntityToPokemon(savedPokemonEntity));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found or Pokémon limit reached");
        }
    }

    @Override
    public Optional<Pokemon> updatePokemonForUser(Long userId, Long pokemonId, Pokemon updatedPokemon) {
        Optional<PokemonEntity> existingPokemonEntity = pokemonsRepository.findByIdAndUserId(pokemonId, userId);
        if (existingPokemonEntity.isPresent()) {
            PokemonEntity pokemonEntityToUpdate = existingPokemonEntity.get();
            pokemonEntityToUpdate.setName(updatedPokemon.getName());
            pokemonEntityToUpdate.setType(updatedPokemon.getType());

            PokemonEntity updatedPokemonEntity = pokemonsRepository.save(pokemonEntityToUpdate);
            return Optional.ofNullable(userMapper.pokemonEntityToPokemon(updatedPokemonEntity));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pokemon not found");
        }
    }

    @Override
    public Optional<User> updateUser(User user) {
        Optional<User> existingUser = getUserById(user.getId());
        return existingUser.map(userToUpdate -> {
            userToUpdate.setFirstname(user.getFirstname());
            userToUpdate.setLastname(user.getLastname());

            UserEntity updatedUserEntity = userMapper.userToUserEntity(userToUpdate);
            UserEntity savedEntity = usersRepository.save(updatedUserEntity);

            return userMapper.userEntityToUser(savedEntity);
        });
    }

    @Override
    public void deleteUser(Long userId) {
//        log.warn("Deleting user with ID: {}", userId);
        usersRepository.deleteById(userId);
    }
}