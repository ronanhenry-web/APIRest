package com.example.example.service.users;

import com.example.example.model.Pokemon;
import com.example.example.model.User;
import com.example.example.repository.model.PokemonEntity;
import com.example.example.repository.model.UserEntity;
import com.example.example.repository.pokemons.PokemonsRepository;
import com.example.example.repository.users.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UsersServiceImplTest {
    @InjectMocks
    private UsersServiceImpl usersService;

    @Mock
    private UsersRepository usersRepositoryMock;

    @Mock
    private PokemonsRepository pokemonsRepositoryMock;

    @Captor
    ArgumentCaptor<UserEntity> usersServiceArgumentCaptor;

    @Captor
    ArgumentCaptor<PokemonEntity> pokemonsArgumentCaptor;

    @Test
    public void testGetAllUsers() {
        // Arrange
        List<UserEntity> userEntitiesMocked = List.of(
                UserEntity.builder().id(1L).firstname("Riri").lastname("Roro").build(),
                UserEntity.builder().id(2L).firstname("Toto").lastname("Tata").build()
        );

        when(usersRepositoryMock.findAll()).thenReturn(userEntitiesMocked);

        // Act
        List<User> usersToAssert = usersService.getAllUsers();

        // Assert
        assertEquals(2, (usersToAssert).size());
        verify(usersRepositoryMock, times(1)).findAll();
    }

    @Test
    public void testGetUserByIdIfExisting() {
        // Arrange
        when(usersRepositoryMock.findById(1L)).thenReturn(Optional.of(
                UserEntity.builder().id(1L).firstname("Roro").lastname("Rara").build()
        ));

        // Act
        Optional<User> userToAssert = usersService.getUserById(1L);

        // Assert
        assertTrue(userToAssert.isPresent());
        assertEquals("Roro", userToAssert.get().getFirstname());
        verify(usersRepositoryMock, times(1)).findById(1L);
    }

    @Test
    public void testGetUserByIdIfNotExisting() {
        // Arrange
        when(usersRepositoryMock.findById(3L)).thenReturn(Optional.empty());

        // Act
        Optional<User> userToAssert = usersService.getUserById(3L);

        // Assert
        assertEquals(Optional.empty(), userToAssert);
        verify(usersRepositoryMock, times(1)).findById(3L);
    }

    @Test
    public void testCreateUser() {
        // Arrange
        UserEntity userToCreate = UserEntity.builder().id(1L).firstname("Riri").lastname("Roro").build();


        when(usersRepositoryMock.save(usersServiceArgumentCaptor.capture()))
                .thenReturn(userToCreate);

        assertEquals("Riri", userToCreate.getFirstname());
        assertEquals("Roro", userToCreate.getLastname());

        // Act
        User userToAssert = usersService.createUser(User.builder()
                .id(userToCreate.getId())
                .firstname(userToCreate.getFirstname())
                .lastname(userToCreate.getLastname())
                .build());

        // Assert
        verify(usersRepositoryMock, times(1)).save(usersServiceArgumentCaptor.capture());

        assertNotNull(userToAssert);
        UserEntity capturedUserEntity = usersServiceArgumentCaptor.getValue();
        assertEquals("Riri", capturedUserEntity.getFirstname());
        assertEquals("Roro", capturedUserEntity.getLastname());
    }


    @Test
    void testUpdateUser() {
        // Arrange
        UserEntity userToUpdate = UserEntity.builder().id(1L).firstname("Riri").lastname("Roro").build();
        when(usersRepositoryMock.findById(1L)).thenReturn(Optional.of(userToUpdate));
        User updatedUser = User.builder()
                .id(userToUpdate.getId())
                .firstname("Riirii")
                .lastname("Rooroo")
                .build();
        when(usersRepositoryMock.save(usersServiceArgumentCaptor.capture())).thenAnswer(invocation -> {
            UserEntity savedUserEntity = invocation.getArgument(0);

            assertEquals("Riirii", savedUserEntity.getFirstname());
            assertEquals("Rooroo", savedUserEntity.getLastname());

            return UserEntity.builder()
                    .id(savedUserEntity.getId())
                    .firstname(savedUserEntity.getFirstname())
                    .lastname(savedUserEntity.getLastname())
                    .build();
        });

        // Act
        Optional<User> userToAssert = usersService.updateUser(updatedUser);

        // Assert
        verify(usersRepositoryMock, times(1)).save(usersServiceArgumentCaptor.capture());
        assertTrue(userToAssert.isPresent());
        UserEntity capturedUserEntity = usersServiceArgumentCaptor.getValue();
        assertEquals("Riirii", capturedUserEntity.getFirstname());
        assertEquals("Rooroo", capturedUserEntity.getLastname());
    }

    @Test
    void testUpdatePokemonForUser() {
        // Arrange
        Long userId = 1L;
        Long pokemonId = 1L;

        PokemonEntity pokemonToUpdate = PokemonEntity.builder()
                .id(pokemonId)
                .name("Pikachu")
                .type("Electric")
                .build();

        when(pokemonsRepositoryMock.findByIdAndUsers_Id(pokemonId, userId))
                .thenReturn(Optional.of(pokemonToUpdate));

        assertEquals("Pikachu", pokemonToUpdate.getName());
        assertEquals("Electric", pokemonToUpdate.getType());

        Pokemon updatedPokemon = Pokemon.builder()
                .id(pokemonId)
                .name("Raichu")
                .type("Electric")
                .build();

        when(pokemonsRepositoryMock.save(pokemonsArgumentCaptor.capture()))
                .thenReturn(pokemonToUpdate);

        // Act
        Optional<Pokemon> pokemonToAssert = usersService.updatePokemonForUser(userId, pokemonId, updatedPokemon);

        // Assert
        verify(pokemonsRepositoryMock, times(1)).save(pokemonsArgumentCaptor.capture());

        assertNotNull(pokemonToAssert);
        PokemonEntity capturedPokemonEntity = pokemonsArgumentCaptor.getValue();
        assertEquals("Raichu", capturedPokemonEntity.getName());
        assertEquals("Electric", capturedPokemonEntity.getType());
    }

    @Test
    void testDeleteUser() {
        // Arrange
        Long userId = 1L;

        when(usersRepositoryMock.findById(userId)).thenReturn(Optional.of(UserEntity.builder().id(userId).build()));

        // Act
        usersService.deleteUser(userId);

        // Assert
        verify(usersRepositoryMock, times(1)).deleteById(userId);
    }
}
