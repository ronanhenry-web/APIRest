package com.example.example.repository.pokemons;

import com.example.example.repository.model.PokemonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonsRepository extends CrudRepository<PokemonEntity, Long> {
}
