package com.gevorg.main.repositories;

import com.gevorg.main.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> getAllByStartDateIsBeforeAndExpirationDateIsAfter(LocalDateTime expectedAfter, LocalDateTime expectedBefore);
}
