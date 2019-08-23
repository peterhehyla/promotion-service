package com.hylamobile.promotion.dto;

import com.hylamobile.promotion.enums.CategoryQuestionCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionResponseDTO implements QuestionResponse {
    private CategoryQuestionCode categoryQuestionCode;
    private String responseCode;
}
