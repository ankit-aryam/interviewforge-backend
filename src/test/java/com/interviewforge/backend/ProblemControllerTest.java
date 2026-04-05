package com.interviewforge.backend;

import com.interviewforge.backend.entity.Problem;
import com.interviewforge.backend.repository.ProblemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProblemControllerTest extends BaseIntegrationTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Test
    void shouldCreateProblem() {

        Problem problem = Problem.builder()
                .title("Two Sum")
                .description("Find two numbers")
                .difficulty("EASY")
                .tags("ARRAY")
                .createdAt(LocalDateTime.now())
                .build();

        Problem saved = problemRepository.save(problem);

        assertNotNull(saved.getId());
        assertEquals("Two Sum", saved.getTitle());
    }
}
