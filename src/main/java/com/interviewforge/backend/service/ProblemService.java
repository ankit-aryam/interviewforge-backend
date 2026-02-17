package com.interviewforge.backend.service;

import com.interviewforge.backend.entity.Problem;
import com.interviewforge.backend.repository.ProblemRepository;
import com.interviewforge.backend.specification.ProblemSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    public Page<Problem> getProblems(
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

        return problemRepository.findAll(spec, pageable);
    }
}
