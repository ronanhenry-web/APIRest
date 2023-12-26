package com.example.example.service.examples;

import com.example.example.model.Example;

import java.util.List;
import java.util.Optional;

public interface ExamplesService {

    /**
     * Return all examples
     *
     * @return
     */
    List<Example> getAllExamples();

    /**
     * Return example by id
     *
     * @param id
     * @return
     */
    Optional<Example> getExampleById(Long id);

    /**
     * Return update example
     *
     * @param example
     * @return
     */
    Optional<Example> updateExample(Long id, Example example);
}
