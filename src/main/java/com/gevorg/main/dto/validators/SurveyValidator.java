package com.gevorg.main.dto.validators;

import com.gevorg.main.domain.Question;
import com.gevorg.main.domain.Survey;
import com.gevorg.main.dto.survey.SurveyCreationDto;
import com.gevorg.main.dto.survey.SurveyModificationDto;
import com.gevorg.main.dto.survey.SurveyPassingDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;

public class SurveyValidator {
    public static final String PREFIX = "Not valid survey: ";

    public static void validate(SurveyCreationDto survey) {
        if (survey.getTitle() == null) {
            throw new IllegalArgumentException(PREFIX + " name cannot be null");
        }
        if (survey.getDescription() == null) {
            throw new IllegalArgumentException(PREFIX + " description cannot be null");
        }
        if (survey.getStartDate() == null) {
            throw new IllegalArgumentException(PREFIX + " start date cannot be null");
        }
        LocalDateTime now = now();
        if (survey.getStartDate().isBefore(now)) {
            throw new IllegalArgumentException(PREFIX + " start date cannot be before current time");
        }
        if (survey.getExpirationDate() == null) {
            throw new IllegalArgumentException(PREFIX + " expiration date cannot be null");
        }
        if (survey.getExpirationDate().isBefore(now)) {
            throw new IllegalArgumentException(PREFIX + " expiration date cannot be before current time");
        }
        if (!survey.getExpirationDate().isAfter(survey.getStartDate())) {
            throw new IllegalArgumentException(PREFIX + " expiration date cannot be before start date");
        }
    }

    public static void validate(SurveyModificationDto surveyModificationDto, LocalDateTime startDate) {
        if (surveyModificationDto.getTitle() == null) {
            throw new IllegalArgumentException(PREFIX + " name cannot be null");
        }
        if (surveyModificationDto.getDescription() == null) {
            throw new IllegalArgumentException(PREFIX + " description cannot be null");
        }
        LocalDateTime now = now();
        if (surveyModificationDto.getExpirationDate() == null) {
            throw new IllegalArgumentException(PREFIX + " expiration date cannot be null");
        }
        if (surveyModificationDto.getExpirationDate().isBefore(now)) {
            throw new IllegalArgumentException(PREFIX + " expiration date cannot be before current time");
        }
        if (!surveyModificationDto.getExpirationDate().isAfter(startDate)) {
            throw new IllegalArgumentException(PREFIX + " expiration date cannot be before start date");
        }
    }

    public static void validate(SurveyPassingDto surveyPassingDto, Survey survey) {
        if (surveyPassingDto.getConsumerID() == null) {
            throw new IllegalArgumentException(PREFIX + " consumer id cannot be null");
        }
        if (surveyPassingDto.getAnswers() == null || surveyPassingDto.getAnswers().isEmpty()) {
            throw new IllegalArgumentException(PREFIX + " answers cannot be empty");
        }
        for (SurveyPassingDto.AnswerDto answerDto : surveyPassingDto.getAnswers()) {
            Long questionId = answerDto.getQuestionId();
            Optional<Question> foundQuestion = survey.getQuestions().stream().filter(q -> q.getId() == questionId).findFirst();
            if (foundQuestion.isEmpty()) {
                throw new IllegalArgumentException("NO question in survey found with id " + questionId);
            }
            Question question = foundQuestion.get();
            switch (question.getType()) {
                case ANSWER_WITH_TEXT -> {
                    String answerText = answerDto.getAnswerText();
                    if (answerText == null || answerText.trim().isEmpty()) {
                        throw new IllegalArgumentException("No answer provided for question with type ANSWER_WITH_TEXT questionID:" + questionId);
                    }
                }
                case ANSWER_WITH_ONE_OPTION -> {
                    List<String> answers = answerDto.getAnswers();
                    if (answers.size() > 1) {
                        throw new IllegalArgumentException("Question with type ANSWER_WITH_ONE_OPTION must have one answer option questionID:" + questionId);
                    }
                    String answer = answers.get(0);
                    if (question.getAnswerOptions().stream().noneMatch(it -> it.getAnswerText().equals(answer))) {
                        throw new IllegalArgumentException(format("Provided answer option must present in question answer options questionID:%s, answer:%s", questionId, answer));
                    }
                }
                case ANSWER_WITH_MULTIPLE_OPTIONS -> {
                    List<String> answers = answerDto.getAnswers();
                    answers.forEach(answerText -> {
                        if (question.getAnswerOptions().stream().noneMatch(it -> it.getAnswerText().equals(answerText))) {
                            throw new IllegalArgumentException(format("Provided answer option must present in question answer options questionID:%s, answer:%s", questionId, answerText));
                        }
                    });
                }
            }

        }

    }
}
