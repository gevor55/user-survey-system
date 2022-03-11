package com.gevorg.main.repositories;

import com.gevorg.main.domain.ConsumerAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerAnswersRepository extends JpaRepository<ConsumerAnswer, Long> {
}
