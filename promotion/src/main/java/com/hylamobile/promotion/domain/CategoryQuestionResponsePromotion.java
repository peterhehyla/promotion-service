package com.hylamobile.promotion.domain;

import com.hylamobile.promotion.dto.QuestionResponse;
import com.hylamobile.promotion.enums.CategoryQuestionCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ref_categoryquestion_response database table.
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class CategoryQuestionResponsePromotion implements Serializable,Comparable<CategoryQuestionResponsePromotion>,
        QuestionResponse {
    private static final long serialVersionUID = 8979877754661L;

    @Id
    @Column(name="categoryquestionresponseid", unique=true, nullable=false)
    private Long categoryQuestionResponseId;
    private CategoryQuestionCode categoryQuestionCode;
    private String responseCode;
    private Long promotionId;
    @Override
    public int compareTo(CategoryQuestionResponsePromotion response){
        if(categoryQuestionResponseId==null){
            return -1;
        }
        if(response.categoryQuestionResponseId==null){
            return 1;
        }
        return (int)(categoryQuestionResponseId.longValue()-response.categoryQuestionResponseId.longValue());
    }
}
