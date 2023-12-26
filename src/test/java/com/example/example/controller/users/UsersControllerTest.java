package com.example.example.controller.users;

import com.example.example.model.User;
import com.example.example.service.users.UsersServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UsersControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UsersServiceImpl usersServiceMock;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllUsers() throws Exception {
        // Arrange
        List<User> usersMocked = List.of(
                User.builder().id(1L).firstname("Riri").lastname("Roro").build(),
                User.builder().id(2L).firstname("Toto").lastname("Tata").build()
        );

        when(usersServiceMock.getAllUsers()).thenReturn(usersMocked);

        // Act
        MvcResult resultToAssert = mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        List<User> usersToAssert = objectMapper.readValue(resultToAssert.getResponse().getContentAsString(), new TypeReference<List<User>>() {
        });

        assertEquals(2, usersToAssert.size());
        User user1 = usersToAssert.get(0);
        assertEquals(1L, user1.getId());
        assertEquals("Riri", user1.getFirstname());
        assertEquals("Roro", user1.getLastname());

        User user2 = usersToAssert.get(1);
        assertEquals(2L, user2.getId());
        assertEquals("Toto", user2.getFirstname());
        assertEquals("Tata", user2.getLastname());

        verify(usersServiceMock, times(1)).getAllUsers();
    }

    @Test
    public void testCreateUser() throws Exception {
        // Arrange
        Long userId = 1L;
        String createFirstname = "Roro";
        String createLastname = "Riri";

        User userMocked = User.builder()
                .id(userId)
                .firstname(createFirstname)
                .lastname(createLastname)
                .build();

        when(usersServiceMock.createUser(any())).thenReturn(userMocked);

        // Act
        mvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstname\": \"" + createFirstname + "\", \"lastname\": \"" + createLastname + "\"}"))
                // Assert
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(createFirstname))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(createLastname));
        verify(usersServiceMock, times(1)).createUser(any());
    }

    @Test
    void testUpdateUser() throws Exception {
        // Arrange
        Long userId = 1L;
        String updatedFirstname = "Roro";
        String updatedLastname = "Ruru";

        User userMocked = User.builder()
                .id(userId)
                .firstname(updatedFirstname)
                .lastname(updatedLastname)
                .build();

        when(usersServiceMock.updateUser(any())).thenReturn(Optional.of(userMocked));

        // Act
        mvc.perform(MockMvcRequestBuilders.patch("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userMocked)))
                // Assert
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(updatedFirstname))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(updatedLastname));
        verify(usersServiceMock, times(1)).updateUser(any());
    }

    @Test
    void testDeleteUser() throws Exception {
        // Arrange
        Long userIdToDelete = 1L;

        // Act
        mvc.perform(MockMvcRequestBuilders.delete("/users/{id}", userIdToDelete)
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(usersServiceMock).deleteUser(userIdToDelete);
    }
}
