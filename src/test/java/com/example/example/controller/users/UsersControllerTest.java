package com.example.example.controller.users;

import com.example.example.model.User;
import com.example.example.service.users.UsersServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        assertTrue(usersToAssert.stream().anyMatch(user ->
                user.getId() == 1L && user.getFirstname().equals("Riri") && user.getLastname().equals("Roro")));
        assertTrue(usersToAssert.stream().anyMatch(user ->
                user.getId() == 2L && user.getFirstname().equals("Toto") && user.getLastname().equals("Tata")));

        verify(usersServiceMock, times(1)).getAllUsers();
    }
}
