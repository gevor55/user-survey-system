package com.gevorg.main.controller;

import com.gevorg.main.dto.survey.SurveyCreationDto;
import com.gevorg.main.dto.survey.SurveyDto;
import com.gevorg.main.dto.survey.SurveyModificationDto;
import com.gevorg.main.services.SurveyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SurveyController {
    public final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping("/survey")
    @PreAuthorize("hasRole('ADMIN')")
    public SurveyDto createSurvey(@RequestBody SurveyCreationDto surveyCreationDto) {
        return surveyService.create(surveyCreationDto);
    }

    @GetMapping("/surveys")
    @PreAuthorize("hasRole('ADMIN')")
    public List<SurveyDto> getSurveyList() {
        return surveyService.findAll();
    }

    @PutMapping("/survey/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SurveyDto updateSurvey(@PathVariable Long id, @RequestBody SurveyModificationDto surveyModificationDto) {
        return surveyService.update(id, surveyModificationDto);
    }

    @DeleteMapping("/survey{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSurvey(@PathVariable int id) {
        surveyService.delete(id);
    }

}
