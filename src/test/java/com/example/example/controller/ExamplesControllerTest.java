package com.example.example.controller;

import com.example.example.controller.examples.ExamplesController;
import com.example.example.model.Example;
import com.example.example.service.examples.ExamplesServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExamplesController.class)
class ExamplesControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ExamplesServiceImpl examplesServiceMock;
    @Captor
    ArgumentCaptor<Example> examplesArgumentCaptor;

    @Test
    void testGetAllExamples() throws Exception {
        // Arrange
        List<Example> exampleDataMocked = List.of(
                new Example(1L, "Exemple 1"),
                new Example(2L, "Exemple 2")
        );

        when(examplesServiceMock.getAllExamples()).thenReturn(exampleDataMocked);

        // Act
        mvc.perform(get("/examples"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].message").value("Exemple 1"));
    }

    @Test
    void testGetExampleById() throws Exception {
        // Arrange
        Example exampleMocked = new Example(3L, "Exemple 3");

        when(examplesServiceMock.getExampleById(3L)).thenReturn(Optional.of(exampleMocked));

        // Act
        mvc.perform(get("/examples/3"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.message").value("Exemple 3"));
    }

    @Test
    void testUpdateExample() throws Exception {
        // Arrange
        Long exampleId = 1L;
        String updatedMessage = "Updated Message";

        Example exampleMocked = new Example(exampleId, updatedMessage);

        when(examplesServiceMock.updateExample(any(), any())).thenReturn(Optional.of(exampleMocked));

        // Act
        mvc.perform(MockMvcRequestBuilders.put("/examples/{id}", exampleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"" + updatedMessage + "\"}"))
                // Assert
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(exampleId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(updatedMessage));

        verify(examplesServiceMock).updateExample(any(), examplesArgumentCaptor.capture());
    }
}