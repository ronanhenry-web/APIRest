package com.example.example.service.examples;

import ch.qos.logback.classic.Logger;
import com.example.example.model.Example;
import com.example.example.repository.examples.ExamplesRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamplesServiceImpl implements ExamplesService {
    private final ExamplesRepository examplesRepository;

    public ExamplesServiceImpl(ExamplesRepository examplesRepository) {
        this.examplesRepository = examplesRepository;
    }

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ExamplesServiceImpl.class);

    @Override
    public List<Example> getAllExamples() {
        return examplesRepository.getAllExamples();
    }

    @Override
    public Optional<Example> getExampleById(Long id) {
        LOGGER.debug("ExampleService by id : {}", id);
        return examplesRepository.getExampleById(id);
    }

    @Override
    public Optional<Example> updateExample(Long id, Example example) {
        return examplesRepository.updateExample(id, example);
    }
}
