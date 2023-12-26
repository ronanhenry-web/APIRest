package com.example.example.controller.examples;

import com.example.example.model.Example;
import com.example.example.service.examples.ExamplesServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/examples")
public class ExamplesController {

    @Autowired
    private ExamplesServiceImpl exampleServiceImp;

    @GetMapping
    @Operation(summary = "All examples")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found examples",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Example.class))}),
            @ApiResponse(responseCode = "404", description = "Examples not found",
                    content = @Content),
    })
    public List<Example> getAllExamples() {
        return exampleServiceImp.getAllExamples();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Example by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the example",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Example.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Example not found",
                    content = @Content),
    })
    public Optional<Example> getExampleById(@PathVariable Long id) {
        return exampleServiceImp.getExampleById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Example> updateExample(@PathVariable Long id, @RequestBody Example updateExample) {
        Optional<Example> optionalExample = exampleServiceImp.updateExample(id, updateExample);
        return optionalExample.map(example -> new ResponseEntity<>(example, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }
}