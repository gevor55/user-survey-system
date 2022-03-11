package com.gevorg.main.dto.mapper;

import com.gevorg.main.domain.AnswerOption;
import com.gevorg.main.domain.Question;
import com.gevorg.main.domain.Survey;
import com.gevorg.main.dto.question.*;

import java.util.List;

public class QuestionMapper {
    public static Question map(QuestionCreationDto dto, Survey survey) {
        Question question = new Question();
        question.setText(dto.getText());
        question.setType(dto.getType());
        question.setSurvey(survey);
        return question;
    }

    public static QuestionDto map(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setSurveyId(question.getSurvey().getId());
        questionDto.setText(question.getText());
        questionDto.setType(question.getType());
        List<AnswerOption> answerOptions = question.getAnswerOptions();
        if (answerOptions != null) {
            questionDto.setAnswerOptions(answerOptions.stream().map(AnswerOption::getAnswerText).toList());
        }
        return questionDto;
    }

}
