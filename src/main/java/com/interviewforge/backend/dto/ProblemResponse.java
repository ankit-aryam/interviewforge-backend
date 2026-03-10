package com.interviewforge.backend.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemResponse implements Serializable {

    private Long id;
    private String title;
    private String difficulty;
    private String tags;
    private LocalDateTime createdAt;
}
