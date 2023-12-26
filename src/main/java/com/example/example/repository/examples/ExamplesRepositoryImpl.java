package com.example.example.repository.examples;

import ch.qos.logback.classic.Logger;
import com.example.example.model.Example;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ExamplesRepositoryImpl implements ExamplesRepository {
    private static final List<Example> EXAMPLES = new ArrayList<>();
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ExamplesRepositoryImpl.class);

    static {
        for (long i = 0; i < 10; i++) {
            EXAMPLES.add(new Example(i, "Ceci est l'exemple avec pour identifiant : " + i));
        }
    }

    @Override
    public List<Example> getAllExamples() {
        LOGGER.warn("All returned examples repository");
        return EXAMPLES;
    }

    @Override
    public Optional<Example> getExampleById(Long id) {
        LOGGER.warn("ExampleRepository by id : {}", id);
        return EXAMPLES.stream()
                .filter(example -> example.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Example> updateExample(Long id, Example updatedExample) {
        Optional<Example> exampleOptional = getExampleById(id);
        exampleOptional.ifPresent(example -> example.setMessage(updatedExample.getMessage()));
        return exampleOptional;
    }
}
