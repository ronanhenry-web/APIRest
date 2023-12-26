package com.example.example.controller.users;

import com.example.example.model.Pokemon;
import com.example.example.model.User;
import com.example.example.service.pokemons.PokemonsService;
import com.example.example.service.users.UsersService;
import com.example.example.utils.exceptions.FunctionalExceptionType;
import com.example.example.utils.exceptions.FunctionalRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    private final PokemonsService pokemonsService;

    public UsersController(UsersService usersService, PokemonsService pokemonsService) {
        this.usersService = usersService;
        this.pokemonsService = pokemonsService;
    }

    @GetMapping
    public List<User> getAll() {
        return usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        Optional<User> userOptional = usersService.getUserById(id);

        return userOptional
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new FunctionalRuntimeException(
                        "User not found with id: " + id,
                        FunctionalExceptionType.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User createdUser = usersService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/{id}/pokemons")
    public ResponseEntity<Void> addPokemonToUser(
            @PathVariable Long id,
            @RequestBody Pokemon pokemon
    ) {
        Optional<Optional<Pokemon>> addedPokemon = Optional.ofNullable(usersService.addPokemonToUser(id, pokemon));
        return addedPokemon.isPresent() ?
                ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/{id}/pokemons/{pokemonId}")
    public ResponseEntity<Pokemon> updatePokemon(
            @PathVariable Long id,
            @PathVariable Long pokemonId,
            @RequestBody Pokemon updatedPokemon) {
        return usersService.updatePokemonForUser(id, pokemonId, updatedPokemon)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return usersService.updateUser(user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/pokemons/{pokemonId}")
    public ResponseEntity<Void> deletePokemon(@PathVariable Long userId, @PathVariable Long pokemonId) {
        pokemonsService.deletePokemon(userId, pokemonId);
        return ResponseEntity.noContent().build();
    }
}
