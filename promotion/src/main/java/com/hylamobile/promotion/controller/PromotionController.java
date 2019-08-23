package com.hylamobile.promotion.controller;

import com.hylamobile.promotion.domain.Promotion;
import com.hylamobile.promotion.dto.CustomerDTO;
import com.hylamobile.promotion.dto.PromotionDTO;
import com.hylamobile.promotion.dto.PromotionQualifierDTO;
import com.hylamobile.promotion.dto.PromotionSearchDTO;
import com.hylamobile.promotion.service.PromotionCriterionService;
import com.hylamobile.promotion.service.PromotionService;
import org.dozer.DozerBeanMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PromotionController {
    @Resource
    private PromotionService promotionService;
    @Resource
    private PromotionCriterionService promotionCriterionService;
    @Resource
    private DozerBeanMapper dozerBeanMapper;
    @PostMapping(value = "/{program}/promotions")
    @ResponseBody
    public List<PromotionDTO> retrieveAvailablePromotions(
            @PathVariable String program,
            @RequestBody PromotionSearchDTO promotionSearch){
        List<Promotion> promotions = promotionService.getValidPromotions(promotionSearch);
        return promotions.stream().map(p->dozerBeanMapper.map(p, PromotionDTO.class)).collect(Collectors.toList());
    }
    @PostMapping(value = "/{program}/criterions")
    @ResponseBody
    public List<PromotionQualifierDTO> findGroupCriteria(CustomerDTO customer){
        return promotionCriterionService.findPromotionQualifier(customer);
    }
}
