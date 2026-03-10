package com.interviewforge.backend.service;

import com.interviewforge.backend.dto.CreateProblemRequest;
import com.interviewforge.backend.dto.ProblemResponse;
import com.interviewforge.backend.entity.Problem;
import com.interviewforge.backend.mapper.ProblemMapper;
import com.interviewforge.backend.repository.ProblemRepository;
import com.interviewforge.backend.specification.ProblemSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    public Page<ProblemResponse> getProblems(
            String difficulty,
            String tag,
            Pageable pageable
    ) {

        Specification<Problem> spec =
                Specification.where(
                        ProblemSpecification.hasDifficulty(difficulty)
                ).and(
                        ProblemSpecification.hasTag(tag)
                );

        Page<Problem> problems = problemRepository.findAll(spec, pageable);
        return problems.map(ProblemMapper::toResponse);
    }

    public ProblemResponse createProblem(CreateProblemRequest request) {

        Problem problem = Problem.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .difficulty(request.getDifficulty())
                .tags(request.getTags())
                .createdAt(LocalDateTime.now())
                .build();

        Problem saved = problemRepository.save(problem);

        return ProblemMapper.toResponse(saved);
    }
}
