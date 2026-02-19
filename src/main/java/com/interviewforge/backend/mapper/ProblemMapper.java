package com.interviewforge.backend.mapper;

import com.interviewforge.backend.dto.ProblemResponse;
import com.interviewforge.backend.entity.Problem;

public class ProblemMapper {

    public static ProblemResponse toResponse(Problem problem){
        return ProblemResponse.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .difficulty(problem.getDifficulty())
                .tags(problem.getTags())
                .createdAt(problem.getCreatedAt())
                .build();
    }
}
