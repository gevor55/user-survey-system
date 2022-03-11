package com.gevorg.main.services;

import com.gevorg.main.domain.ConsumerPassedSurvey;
import com.gevorg.main.dto.PassedSurveyDto;
import com.gevorg.main.dto.mapper.ConsumerMapper;
import com.gevorg.main.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    private final ConsumerPassedSurveysRepository consumerPassedSurveysRepository;

    public ConsumerServiceImpl(ConsumerPassedSurveysRepository consumerPassedSurveysRepository) {
        this.consumerPassedSurveysRepository = consumerPassedSurveysRepository;
    }

    @Override
    public List<PassedSurveyDto> findAllPassed(Long consumerId) {
        List<ConsumerPassedSurvey> passedSurveys = consumerPassedSurveysRepository.findAllByConsumerId(consumerId);

        return ConsumerMapper.map(passedSurveys);
    }
}
