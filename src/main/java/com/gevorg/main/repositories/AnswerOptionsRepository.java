package com.gevorg.main.repositories;

import com.gevorg.main.domain.AnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerOptionsRepository extends JpaRepository<AnswerOption, Long> {
}
