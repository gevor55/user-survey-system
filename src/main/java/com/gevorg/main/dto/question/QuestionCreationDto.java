package com.gevorg.main.dto.question;

import com.gevorg.main.domain.QuestionType;

import java.util.Set;

public class QuestionCreationDto {

    private String text;
    private QuestionType type;
    private Long surveyId;

    private Set<String> answerOptions;

    public QuestionCreationDto() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Set<String> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(Set<String> answerOptions) {
        this.answerOptions = answerOptions;
    }
}
