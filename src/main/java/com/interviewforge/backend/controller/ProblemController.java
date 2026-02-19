package com.interviewforge.backend.controller;

import com.interviewforge.backend.dto.CreateProblemRequest;
import com.interviewforge.backend.dto.ProblemResponse;
import com.interviewforge.backend.entity.Problem;
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

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping
    public Page<ProblemResponse> getProblems(
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return problemService.getProblems(difficulty, tag, pageable);
    }

    @PostMapping
    public ResponseEntity<ProblemResponse> createProblem(
            @RequestBody @Valid CreateProblemRequest request
            ){
        ProblemResponse response = problemService.createProblem(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
