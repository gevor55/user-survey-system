package com.gevorg.main.dto.validators;

import com.gevorg.main.dto.question.*;

public class QuestionValidator {
    public static final String PREFIX = "Not valid : ";
    public static void validate(QuestionCreationDto questionDto) {
        if (questionDto.getText()==null) {
            throw new IllegalArgumentException(PREFIX + " question text cannot be null");
        }
        if (questionDto.getType()==null) {
            throw new IllegalArgumentException(PREFIX + " question type cannot be null");
        }
    }

    public static void validate(QuestionModificationDto questionModificationDto) {
        if (questionModificationDto.getText()==null) {
            throw new IllegalArgumentException(PREFIX + " question text cannot be null");
        }
    }
}
