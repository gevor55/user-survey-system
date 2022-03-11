package com.gevorg.main.repositories;

import com.gevorg.main.domain.ConsumerPassedSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsumerPassedSurveysRepository extends JpaRepository<ConsumerPassedSurvey, Long> {
    Optional<ConsumerPassedSurvey> findByConsumerIdAndSurveyId(Long consumerId, Long surveyId);

    List<ConsumerPassedSurvey> findAllByConsumerId(Long consumerId);
}
