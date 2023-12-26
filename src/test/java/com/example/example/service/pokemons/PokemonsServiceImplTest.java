package com.example.example.service.pokemons;

import com.example.example.repository.model.PokemonEntity;
import com.example.example.repository.model.UserEntity;
import com.example.example.repository.pokemons.PokemonsRepository;
import com.example.example.repository.users.UsersRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Optional;

import static org.mockito.Mockito.*;

@WebMvcTest(MockitoExtension.class)
public class PokemonsServiceImplTest {
    @InjectMocks
    private PokemonsServiceImpl pokemonsService;
    @Mock
    private PokemonsRepository pokemonsRepositoryMock;

    @Mock
    private UsersRepository usersRepositoryMock;

    @Test
    void testDeletePokemon() {
        // Arrange
        Long userId = 1L;
        Long pokemonId = 1L;

        when(usersRepositoryMock.findById(userId)).thenReturn(Optional.of(UserEntity.builder().id(userId).build()));
        when(pokemonsRepositoryMock.findById(pokemonId)).thenReturn(Optional.of(PokemonEntity.builder().id(pokemonId).build()));

        // Act
        pokemonsService.deletePokemon(userId, pokemonId);

        // Assert
        verify(pokemonsRepositoryMock, times(1)).deleteById(pokemonId);
    }
}
