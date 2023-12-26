package com.example.example.service.pokemons;

import com.example.example.repository.pokemons.PokemonsRepository;
import com.example.example.repository.users.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PokemonsServiceImpl implements PokemonsService {
    private final PokemonsRepository pokemonsRepository;
    private final UsersRepository usersRepository;

    public PokemonsServiceImpl(PokemonsRepository pokemonsRepository, UsersRepository usersRepository) {
        this.pokemonsRepository = pokemonsRepository;
        this.usersRepository = usersRepository;
    }

    //  Management conflict association table
    @Override
    public void deletePokemon(Long userId, Long pokemonId) {
        log.info("Deleting Pokemon with id {} for user with id {}", pokemonId, userId);

        pokemonsRepository.deleteByIdAndUsers_Id(pokemonId, userId);

        log.info("Pokemon deleted successfully");
    }
}
