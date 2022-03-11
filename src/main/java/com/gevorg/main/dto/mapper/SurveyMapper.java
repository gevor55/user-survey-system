package com.gevorg.main.dto.mapper;

import com.gevorg.main.domain.Question;
import com.gevorg.main.domain.Survey;
import com.gevorg.main.dto.survey.SurveyCreationDto;
import com.gevorg.main.dto.survey.SurveyDto;

import java.util.List;

public class SurveyMapper {
//    public static SurveyPassingDto map(Survey survey) {
//        SurveyPassingDto dto = new SurveyPassingDto();
//        dto.setSurveyId(survey.getId());
//        return null;
//    }

    public static Survey map(SurveyCreationDto dto) {
        Survey survey = new Survey();
        survey.setTitle(dto.getTitle());
        survey.setDescription(dto.getDescription());
        survey.setStartDate(dto.getStartDate());
        survey.setExpirationDate(dto.getExpirationDate());
        return survey;
    }


    public static SurveyDto map(Survey survey) {
        SurveyDto surveyDto = new SurveyDto();
        surveyDto.setId(survey.getId());
        surveyDto.setTitle(survey.getTitle());
        surveyDto.setDescription(survey.getDescription());
        surveyDto.setStartDate(survey.getStartDate());
        surveyDto.setExpirationDate(survey.getExpirationDate());
        List<Question> questions = survey.getQuestions();
        if (questions != null) {
            surveyDto.setQuestions(questions.stream().map(QuestionMapper::map).toList());
        }
        return surveyDto;
    }
}
