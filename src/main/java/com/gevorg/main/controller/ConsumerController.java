package com.gevorg.main.controller;

import com.gevorg.main.dto.PassedSurveyDto;
import com.gevorg.main.dto.survey.SurveyDto;
import com.gevorg.main.dto.survey.SurveyPassingDto;
import com.gevorg.main.services.ConsumerService;
import com.gevorg.main.services.SurveyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    private final SurveyService surveyService;
    private final ConsumerService consumerService;

    public ConsumerController(SurveyService surveyService, ConsumerService consumerService) {
        this.surveyService = surveyService;
        this.consumerService = consumerService;
    }

    @GetMapping("/surveys")
    public List<SurveyDto> getActiveSurveys() {
        return surveyService.findAllActives();
    }

    @PostMapping("/surveys/{id}")
    public void passSurvey(@PathVariable long id, @RequestBody SurveyPassingDto surveyPassingDto) {
        surveyService.pass(id, surveyPassingDto);
    }

    @GetMapping("/passed-surveys/{consumerId}")
    public List<PassedSurveyDto> getAllPassedByConsumerId(@PathVariable Long consumerId) {
        return consumerService.findAllPassed(consumerId);
    }
}
