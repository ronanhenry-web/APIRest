package com.example.example.service.pokemons;

import com.example.example.repository.pokemons.PokemonsRepository;
import com.example.example.utils.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PokemonsServiceImpl implements PokemonsService {
    private final PokemonsRepository pokemonsRepository;
    private final UserMapper userMapper;

    public PokemonsServiceImpl(PokemonsRepository pokemonsRepository, UserMapper userMapper) {
        this.pokemonsRepository = pokemonsRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void deletePokemon(Long pokemonId) {
        pokemonsRepository.deleteById(pokemonId);
    }
}
