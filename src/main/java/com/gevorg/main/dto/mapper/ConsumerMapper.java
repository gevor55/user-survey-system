package com.gevorg.main.dto.mapper;

import com.gevorg.main.domain.*;
import com.gevorg.main.dto.PassedSurveyDto;
import com.gevorg.main.dto.survey.SurveyPassingDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class ConsumerMapper {
    public static ConsumerPassedSurvey map(Survey survey, Long consumerId) {
        ConsumerPassedSurvey consumerPassedSurvey = new ConsumerPassedSurvey();
        consumerPassedSurvey.setSurvey(survey);
        consumerPassedSurvey.setConsumerId(consumerId);
        return consumerPassedSurvey;
    }

    public static List<ConsumerAnswer> map(ConsumerPassedSurvey consumerPassedSurvey, Survey survey, SurveyPassingDto surveyPassingDto) {
        List<ConsumerAnswer> result = new ArrayList<>();
        for (SurveyPassingDto.AnswerDto answerDto : surveyPassingDto.getAnswers()) {

            Long questionId = answerDto.getQuestionId();
            Question question = survey.getQuestions().stream().filter(q -> q.getId() == questionId).findFirst().orElseThrow();

            switch (question.getType()) {
                case ANSWER_WITH_TEXT -> {
                    ConsumerAnswer consumerAnswer = new ConsumerAnswer();
                    consumerAnswer.setConsumerId(surveyPassingDto.getConsumerID());
                    consumerAnswer.setAnswerText(answerDto.getAnswerText());
                    consumerAnswer.setQuestion(question);
                    consumerAnswer.setConsumerPassedSurvey(consumerPassedSurvey);
                    result.add(consumerAnswer);
                }
                case ANSWER_WITH_ONE_OPTION -> {
                    String answerOptionText = answerDto.getAnswers().get(0);
                    AnswerOption answerOption = question.getAnswerOptions()
                            .stream()
                            .filter(it -> it.getAnswerText().equals(answerOptionText))
                            .findFirst().orElseThrow();
                    ConsumerAnswer consumerAnswer = new ConsumerAnswer();
                    consumerAnswer.setConsumerId(surveyPassingDto.getConsumerID());
                    consumerAnswer.setQuestion(question);
                    consumerAnswer.setAnswerOption(answerOption);
                    consumerAnswer.setConsumerPassedSurvey(consumerPassedSurvey);
                    result.add(consumerAnswer);
                }
                case ANSWER_WITH_MULTIPLE_OPTIONS -> result.addAll(
                        answerDto
                                .getAnswers()
                                .stream()
                                .map(answer -> {
                                    ConsumerAnswer consumerAnswer = new ConsumerAnswer();
                                    consumerAnswer.setConsumerId(surveyPassingDto.getConsumerID());
                                    consumerAnswer.setQuestion(question);
                                    consumerAnswer.setAnswerOption(question.getAnswerOptions()
                                            .stream()
                                            .filter(it -> it.getAnswerText().equals(answer))
                                            .findFirst().orElseThrow());
                                    consumerAnswer.setConsumerPassedSurvey(consumerPassedSurvey);
                                    return consumerAnswer;
                                })
                                .toList());

            }
        }
        return result;
    }

    public static List<PassedSurveyDto> map(List<ConsumerPassedSurvey> passedSurveys) {
        return passedSurveys.stream().map(it -> {
            PassedSurveyDto dto = new PassedSurveyDto();
            dto.setSurveyTitle(it.getSurvey().getTitle());
            Map<Question, List<ConsumerAnswer>> questionToAnswersMap = it.getConsumerAnswers()
                    .stream().collect(Collectors.groupingBy(ConsumerAnswer::getQuestion));
            dto.setAnswers(
                    questionToAnswersMap.entrySet().stream().map(entry -> {
                                Question question = entry.getKey();
                                List<ConsumerAnswer> consumerAnswerList = entry.getValue();
                                PassedSurveyDto.QuestionWithAnswerDto questionWithAnswerDto = new PassedSurveyDto.QuestionWithAnswerDto();
                                questionWithAnswerDto.setQuestionText(question.getText());
                                switch (question.getType()) {
                                    case ANSWER_WITH_TEXT -> questionWithAnswerDto
                                            .setAnswerText(consumerAnswerList.get(0).getAnswerText());
                                    case ANSWER_WITH_ONE_OPTION, ANSWER_WITH_MULTIPLE_OPTIONS -> questionWithAnswerDto.setAnsweredOptions(consumerAnswerList
                                            .stream()
                                            .map(answer -> answer.getAnswerOption().getAnswerText())
                                            .toList());
                                }
                                return questionWithAnswerDto;
                            })
                            .toList()
            );
            return dto;
        }).toList();
    }
}
