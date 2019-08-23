package com.hylamobile.promotion.config;

import com.hylamobile.promotion.repository.CategoryQuestionResponsePromotionRepository;
import com.hylamobile.promotion.repository.ModelPromotionRepository;
import com.hylamobile.promotion.repository.PromotionAcctBulkRestrictionRepository;
import com.hylamobile.promotion.repository.PromotionCriterionRepository;
import com.hylamobile.promotion.repository.PromotionEsnRepository;
import com.hylamobile.promotion.repository.PromotionRepository;
import com.hylamobile.promotion.repository.impl.CategoryQuestionResponsePromotionRepositoryImpl;
import com.hylamobile.promotion.repository.impl.ModelPromotionRepositoryImpl;
import com.hylamobile.promotion.repository.impl.PromotionAcctBulkRestrictionRepositoryImpl;
import com.hylamobile.promotion.repository.impl.PromotionCriterionRepositoryImpl;
import com.hylamobile.promotion.repository.impl.PromotionEsnRepositoryImpl;
import com.hylamobile.promotion.repository.impl.PromotionRepositoryImpl;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
public class RepositoryConfig {
    @Bean
    public PromotionRepository promotionRepository(DataSource dataSource){
        PromotionRepositoryImpl promotionRepository = new PromotionRepositoryImpl();
        promotionRepository.setDataSource(dataSource);
        return promotionRepository;
    }

    @Bean
    public PromotionCriterionRepository promotionCriterionRepository(DataSource dataSource){
        PromotionCriterionRepositoryImpl repository = new PromotionCriterionRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }
    @Bean
    public PromotionEsnRepository promotionEsnRepository(DataSource dataSource){
        PromotionEsnRepositoryImpl repository = new PromotionEsnRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }
    @Bean
    public CategoryQuestionResponsePromotionRepository categoryQuestionResponsePromotionRepository(DataSource dataSource){
        CategoryQuestionResponsePromotionRepositoryImpl repository = new CategoryQuestionResponsePromotionRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }
    @Bean
    public ModelPromotionRepository modelPromotionRepository(DataSource dataSource){
        ModelPromotionRepositoryImpl repository = new ModelPromotionRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }
    @Bean
    public PromotionAcctBulkRestrictionRepository promotionAcctBulkRestrictionRepository(DataSource dataSource){
        PromotionAcctBulkRestrictionRepositoryImpl repository = new PromotionAcctBulkRestrictionRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }
    @Bean
    public DozerBeanMapper dozerBeanMapper(){
        return new DozerBeanMapper(Arrays.asList("dozer_mapping.xml"));
    }
}
