package com.gevorg.main.services;

import com.gevorg.main.domain.AnswerOption;
import com.gevorg.main.domain.Question;
import com.gevorg.main.domain.Survey;
import com.gevorg.main.dto.question.*;
import com.gevorg.main.repositories.AnswerOptionsRepository;
import com.gevorg.main.repositories.QuestionRepository;
import com.gevorg.main.repositories.SurveyRepository;
import com.gevorg.main.dto.validators.QuestionValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.gevorg.main.dto.mapper.QuestionMapper.map;
import static com.gevorg.main.dto.validators.QuestionValidator.validate;

@Service
public class QuestionServiceImpl implements QuestionService {
    public final QuestionRepository questionRepository;
    public final SurveyRepository surveyRepository;
    public final AnswerOptionsRepository answerOptionsRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, SurveyRepository surveyRepository, AnswerOptionsRepository answerOptionsRepository) {
        this.questionRepository = questionRepository;
        this.surveyRepository = surveyRepository;
        this.answerOptionsRepository = answerOptionsRepository;
    }

    @Override
    @Transactional
    public QuestionDto create(QuestionCreationDto questionCreationDto) {
        validate(questionCreationDto);
        Survey survey = surveyRepository.findById(questionCreationDto.getSurveyId()).orElseThrow();
        Question newQuestion = map(questionCreationDto, survey);
        questionRepository.save(newQuestion);
        List<AnswerOption> newAnswerOptions = questionCreationDto
                .getAnswerOptions()
                .stream()
                .map(text -> {
                    AnswerOption option = new AnswerOption();
                    option.setQuestion(newQuestion);
                    option.setAnswerText(text);
                    return option;
                }).toList();
        answerOptionsRepository.saveAll(newAnswerOptions);
        newQuestion.setAnswerOptions(newAnswerOptions);
        return map(newQuestion);
    }

    @Override
    public QuestionDto update(long id, QuestionModificationDto questionModificationDto) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (questionOptional.isEmpty()) {
            throw new IllegalArgumentException("Not found id");
        }
        QuestionValidator.validate(questionModificationDto);
        Question question = questionOptional.get();
        populate(question, questionModificationDto);
        questionRepository.save(question);
        return map(question);
    }

    @Override
    public void delete(long id) {
        questionRepository.deleteById(id);
    }

    private void populate(Question question,QuestionModificationDto questionModificationDto) {
        question.setText(questionModificationDto.getText());
    }
}
