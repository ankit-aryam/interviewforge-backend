package com.interviewforge.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemResponse {

    private Long id;
    private String title;
    private String difficulty;
    private String tags;
    private LocalDateTime createdAt;
}
