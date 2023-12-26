package com.example.example.service.pokemons;

import com.example.example.repository.pokemons.PokemonsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PokemonsServiceImpl implements PokemonsService {
    private final PokemonsRepository pokemonsRepository;

    public PokemonsServiceImpl(PokemonsRepository pokemonsRepository) {
        this.pokemonsRepository = pokemonsRepository;
    }

}
