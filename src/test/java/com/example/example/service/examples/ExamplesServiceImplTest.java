package com.example.example.service.examples;

import com.example.example.model.Example;
import com.example.example.repository.examples.ExamplesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ExamplesServiceImplTest {
    @InjectMocks
    private ExamplesServiceImpl examplesService;
    @Mock
    private ExamplesRepository examplesRepositoryMock;
    @Captor
    ArgumentCaptor<Example> examplesServiceArgumentCaptor;

    @Test
    public void testGetAllExamples() {
        // Arrange
        when(examplesRepositoryMock.getAllExamples()).thenReturn(List.of(
                new Example(1L, "Exemple 1")
        ));

        // Act
        List<Example> examplesToAssert = examplesService.getAllExamples();

        // Assert
        assertEquals(1, examplesToAssert.size());
        verify(examplesRepositoryMock, times(1)).getAllExamples();
    }

    @Test
    public void testGetExampleByIdIfExisting() {
        // Arrange
        when(examplesRepositoryMock.getExampleById(1L)).thenReturn(Optional.of(new Example(1L, "Exemple 1")));

        // Act
        Optional<Example> examplesToAssert = examplesService.getExampleById(1L);

        // Assert
        assertTrue(examplesToAssert.isPresent());
        assertEquals("Exemple 1", examplesToAssert.get().getMessage());
        verify(examplesRepositoryMock, times(1)).getExampleById(1L);
    }

    @Test
    public void testGetExampleByIdIfNoExisting() {
        // Arrange
        when(examplesRepositoryMock.getExampleById(3L)).thenReturn(Optional.empty());

        // Act
        Optional<Example> examplesToAssert = examplesService.getExampleById(3L);

        // Assert
        assertEquals(Optional.empty(), examplesToAssert);
        verify(examplesRepositoryMock, times(1)).getExampleById(3L);
    }

    @Test
    public void testUpdateExampleWithArgumentCaptor() {
        // Arrange
        Example exampleToUpdate = new Example(1L, "Exemple 5");
        when(examplesRepositoryMock.updateExample(any(), any())).thenReturn(Optional.of(exampleToUpdate));

        // Act
        examplesService.updateExample(1L, exampleToUpdate);

        // Assert
        verify(examplesRepositoryMock).updateExample(any(), examplesServiceArgumentCaptor.capture());

        Example capturedExample = examplesServiceArgumentCaptor.getValue();
        assertEquals(1L, capturedExample.getId());
        assertEquals("Exemple 5", capturedExample.getMessage());
    }

}