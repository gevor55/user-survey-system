package com.gevorg.main.services;

import com.gevorg.main.dto.survey.SurveyCreationDto;
import com.gevorg.main.dto.survey.SurveyDto;
import com.gevorg.main.dto.survey.SurveyModificationDto;
import com.gevorg.main.dto.survey.SurveyPassingDto;

import java.util.List;

public interface SurveyService {
    SurveyDto create(SurveyCreationDto surveyCreationDto);
    SurveyDto update(Long id, SurveyModificationDto surveyModificationDto);
    void delete(long id);
    List<SurveyDto> findAll();
    List<SurveyDto> findAllActives();
    void pass(long id, SurveyPassingDto surveyPassingDto);
}
