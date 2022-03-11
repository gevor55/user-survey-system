package com.gevorg.main.controller;

import com.gevorg.main.domain.Question;
import com.gevorg.main.services.QuestionService;
import com.gevorg.main.dto.question.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuestionController {
    public final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/question")
    @PreAuthorize("hasRole('ADMIN')")
    public QuestionDto createQuestion(@RequestBody QuestionCreationDto questionCreationDto) {
        return questionService.create(questionCreationDto);
    }

    @PutMapping("/question/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public QuestionDto updateQuestion(@PathVariable Long id, @RequestBody QuestionModificationDto questionModificationDto) {
        return questionService.update(id, questionModificationDto);
    }

    @DeleteMapping("/question{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteQuestion(@PathVariable int id) {
        questionService.delete(id);
    }
}
