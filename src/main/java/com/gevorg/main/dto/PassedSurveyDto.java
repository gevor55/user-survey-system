package com.gevorg.main.dto;

import java.util.List;

public class PassedSurveyDto {

    private String surveyTitle;

    List<QuestionWithAnswerDto> answers;

    public static class QuestionWithAnswerDto {
        private String questionText;
        private String answerText;
        private List<String> answeredOptions;

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public String getAnswerText() {
            return answerText;
        }

        public void setAnswerText(String answerText) {
            this.answerText = answerText;
        }

        public List<String> getAnsweredOptions() {
            return answeredOptions;
        }

        public void setAnsweredOptions(List<String> answeredOptions) {
            this.answeredOptions = answeredOptions;
        }
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public List<QuestionWithAnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuestionWithAnswerDto> answers) {
        this.answers = answers;
    }
}
