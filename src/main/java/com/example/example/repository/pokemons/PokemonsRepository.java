package com.example.example.repository.pokemons;

import com.example.example.repository.model.PokemonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonsRepository extends CrudRepository<PokemonEntity, Long> {
    Optional<PokemonEntity> findByIdAndUserId(Long pokemonId, Long userId);

    void deleteById(Long pokemonId);
}
