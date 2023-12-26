package com.example.example.service.users;

import com.example.example.model.User;
import com.example.example.repository.model.UserEntity;
import com.example.example.repository.users.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {
    @InjectMocks
    private UsersServiceImpl usersService;

    @Mock
    private UsersRepository usersRepositoryMock;

    @Test
    public void testGetAllUsers() {
        // Arrange
        List<UserEntity> userEntitiesMocked = List.of(
                UserEntity.builder().id(1L).firstname("Riri").lastname("Roro").build(),
                UserEntity.builder().id(2L).firstname("Toto").lastname("Tata").build()
        );

        when(usersRepositoryMock.findAll()).thenReturn(userEntitiesMocked);

        // Act
        Iterable<User> usersToAssert = usersService.getAllUsers();

        // Assert
        verify(usersRepositoryMock, times(1)).findAll();

        assertEquals(2, ((List<User>) usersToAssert).size());
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
}
