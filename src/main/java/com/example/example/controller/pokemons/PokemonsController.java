package com.example.example.controller.pokemons;

import com.example.example.service.pokemons.PokemonsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/pokemons")
public class PokemonsController {
    private final PokemonsService pokemonsService;

    public PokemonsController(PokemonsService pokemonsService) {
        this.pokemonsService = pokemonsService;
    }

    @DeleteMapping("/{pokemonId}")
    public ResponseEntity<Void> deletePokemon(@PathVariable Long pokemonId) {
        pokemonsService.deletePokemon(pokemonId);
        return ResponseEntity.noContent().build();
    }
}
