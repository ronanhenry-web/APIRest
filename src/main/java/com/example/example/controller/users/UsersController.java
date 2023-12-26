package com.example.example.controller.users;

import com.example.example.model.Pokemon;
import com.example.example.model.User;
import com.example.example.service.users.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
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
                .orElseGet(() -> ResponseEntity.notFound().build());
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
    public ResponseEntity<Optional<Pokemon>> updatePokemon(
            @PathVariable Long id,
            @PathVariable Long pokemonId,
            @RequestBody Pokemon updatedPokemon) {
        Optional<Pokemon> result = usersService.updatePokemonForUser(id, pokemonId, updatedPokemon);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        Optional<User> updatedUser = usersService.updateUser(user);
        return updatedUser.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
