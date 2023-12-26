package com.example.example.repository.pokemons;

import com.example.example.repository.model.PokemonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonsRepository extends CrudRepository<PokemonEntity, Long> {
    /**
     * @param pokemonId
     * @param userId
     * @return Searches for a Pokémon associated with a user
     */
    Optional<PokemonEntity> findByIdAndUsers_Id(Long pokemonId, Long userId);

    /**
     * Delete the link between a user and a Pokémon
     *
     * @param pokemonId
     * @param userId
     */
    void deleteByIdAndUsers_Id(Long pokemonId, Long userId);
}
