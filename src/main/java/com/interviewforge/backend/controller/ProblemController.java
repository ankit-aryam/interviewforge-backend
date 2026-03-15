package com.interviewforge.backend.controller;

import com.interviewforge.backend.common.dto.ApiResponse;
import com.interviewforge.backend.common.dto.PageResponse;
import com.interviewforge.backend.dto.CreateProblemRequest;
import com.interviewforge.backend.dto.ProblemResponse;
import com.interviewforge.backend.service.ProblemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProblemResponse>>> getProblems(
            @RequestParam(name = "difficulty", required = false) String difficulty,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProblemResponse> pageResult = problemService.getProblems(difficulty, tag, pageable);
        PageResponse<ProblemResponse> pageResponse = PageResponse.from(pageResult);

        ApiResponse<PageResponse<ProblemResponse>> apiResponse =
                ApiResponse.<PageResponse<ProblemResponse>>builder()
                        .success(true)
                        .data(pageResponse)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProblemResponse>> createProblem(
            @RequestBody @Valid CreateProblemRequest request
    ) {

        ProblemResponse response =
                problemService.createProblem(request);

        ApiResponse<ProblemResponse> apiResponse =
                ApiResponse.<ProblemResponse>builder()
                        .success(true)
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiResponse);
    }
}
