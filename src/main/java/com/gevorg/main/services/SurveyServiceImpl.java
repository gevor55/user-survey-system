package com.gevorg.main.services;

import com.gevorg.main.domain.ConsumerPassedSurvey;
import com.gevorg.main.domain.Survey;
import com.gevorg.main.dto.mapper.ConsumerMapper;
import com.gevorg.main.dto.survey.SurveyCreationDto;
import com.gevorg.main.dto.survey.SurveyDto;
import com.gevorg.main.dto.survey.SurveyModificationDto;
import com.gevorg.main.dto.mapper.SurveyMapper;
import com.gevorg.main.dto.survey.SurveyPassingDto;
import com.gevorg.main.repositories.ConsumerAnswersRepository;
import com.gevorg.main.repositories.ConsumerPassedSurveysRepository;
import com.gevorg.main.repositories.SurveyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.gevorg.main.dto.mapper.SurveyMapper.map;
import static com.gevorg.main.dto.validators.SurveyValidator.validate;
import static java.lang.String.format;

@Service
public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepository surveyRepository;
    private final ConsumerPassedSurveysRepository consumerPassedSurveysRepository;
    private final ConsumerAnswersRepository consumerAnswersRepository;


    public SurveyServiceImpl(SurveyRepository surveyRepository, ConsumerPassedSurveysRepository consumerPassedSurveysRepository, ConsumerAnswersRepository consumerAnswersRepository) {
        this.surveyRepository = surveyRepository;
        this.consumerPassedSurveysRepository = consumerPassedSurveysRepository;
        this.consumerAnswersRepository = consumerAnswersRepository;
    }


    @Override
    public SurveyDto create(SurveyCreationDto surveyCreationDto) {
        validate(surveyCreationDto);
        Survey survey = map(surveyCreationDto);
        surveyRepository.save(survey);
        return map(survey);
    }

    @Override
    public SurveyDto update(Long id, SurveyModificationDto surveyModificationDto) {
        Survey survey = surveyRepository.findById(id).orElseThrow();
        validate(surveyModificationDto, survey.getStartDate());
        populate(survey, surveyModificationDto);
        surveyRepository.save(survey);
        return map(survey);
    }

    @Override
    public void delete(long id) {
        surveyRepository.deleteById(id);
    }

    @Override
    public List<SurveyDto> findAll() {
        return surveyRepository.findAll().stream().map(SurveyMapper::map).toList();
    }

    @Override
    public List<SurveyDto> findAllActives() {
        LocalDateTime now = LocalDateTime.now();
        return surveyRepository
                .getAllByStartDateIsBeforeAndExpirationDateIsAfter(now, now)
                .stream().map(SurveyMapper::map).toList();
    }

    @Override
    public void pass(long surveyId, SurveyPassingDto surveyPassingDto) {
        Optional<Survey> surveyOptional = surveyRepository.findById(surveyId);
        if (surveyOptional.isEmpty()) {
            throw new IllegalArgumentException("Not found id");
        }
        Survey survey = surveyOptional.get();
        validate(surveyPassingDto, survey);
        Long consumerId = surveyPassingDto.getConsumerID();
        if (consumerPassedSurveysRepository.findByConsumerIdAndSurveyId(consumerId, surveyId).isPresent()) {
            throw new IllegalArgumentException(format("Consumer with id: %s already passed this survey with id: %s",
                    consumerId, surveyId));
        }
        ConsumerPassedSurvey consumerPassedSurvey = ConsumerMapper.map(survey, consumerId);
        consumerPassedSurveysRepository.save(consumerPassedSurvey);
        consumerAnswersRepository.saveAll(ConsumerMapper.map(consumerPassedSurvey, survey, surveyPassingDto));
    }

    private void populate(Survey survey, SurveyModificationDto surveyModificationDto) {
        survey.setTitle(surveyModificationDto.getTitle());
        survey.setDescription(surveyModificationDto.getDescription());
        survey.setExpirationDate(surveyModificationDto.getExpirationDate());
    }
}
