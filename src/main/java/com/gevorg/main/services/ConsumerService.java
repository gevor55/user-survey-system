package com.gevorg.main.services;

import com.gevorg.main.dto.PassedSurveyDto;

import java.util.List;

public interface ConsumerService {

    List<PassedSurveyDto> findAllPassed(Long consumerId);
}
