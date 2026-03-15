package com.interviewforge.backend.service;

import com.interviewforge.backend.dto.CreateProblemRequest;
import com.interviewforge.backend.dto.ProblemResponse;
import com.interviewforge.backend.entity.Problem;
import com.interviewforge.backend.mapper.ProblemMapper;
import com.interviewforge.backend.repository.ProblemRepository;
import com.interviewforge.backend.specification.ProblemSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemService {

    private final ProblemRepository problemRepository;

    @Cacheable(value = "problems",
            key = "#difficulty + '-' + #tag + '-' + #pageable.pageNumber")
    public Page<ProblemResponse> getProblems(
            String difficulty,
            String tag,
            Pageable pageable
    ) {
        log.info("Checking cache for problems");
        Specification<Problem> spec =
                Specification.where(
                        ProblemSpecification.hasDifficulty(difficulty)
                ).and(
                        ProblemSpecification.hasTag(tag)
                );

        log.info("Fetching problems difficulty={} tag={}", difficulty, tag);
        Page<Problem> problems = problemRepository.findAll(spec, pageable);

        System.out.println("Problems: "+problems);
        return problems.map(ProblemMapper::toResponse);
    }

    @CacheEvict(value = "problems", allEntries = true)
    public ProblemResponse createProblem(CreateProblemRequest request) {

        log.info("Creating new problem title= {}", request.getTitle());

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
