package com.gevorg.main.services;

import com.gevorg.main.dto.question.*;

public interface QuestionService {
    QuestionDto create(QuestionCreationDto question);
    QuestionDto update(long id,QuestionModificationDto question);
    void delete(long id);
}
