package com.hylamobile.promotion.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.hylamobile.promotion.common.SpringMvcIT;
import com.hylamobile.promotion.dto.PromotionDTO;
import com.hylamobile.promotion.dto.PromotionSearchDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@Transactional
@DatabaseSetup(value = "PromotionControllerIT.xml", type = DatabaseOperation.CLEAN_INSERT)
public class PromotionControllerIT extends SpringMvcIT {

	@Test
	public void retrievePromotions() throws Exception {
		PromotionSearchDTO promotionSearch = new PromotionSearchDTO();
		Set<String> modelCodes = new HashSet<>();
		modelCodes.add("123");
		promotionSearch.setAffectedCompanyId(1L);
		promotionSearch.setModelCodes(modelCodes);
		ObjectMapper objectMapper = new ObjectMapper();
		String promotionSearchStr = objectMapper.writeValueAsString(promotionSearch);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/vzw/promotions")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(promotionSearchStr))
				.andExpect(status().isOk())
				.andReturn();
		List<PromotionDTO> promotions = objectMapper.readValue(result.getResponse().getContentAsString(),new TypeReference<List<PromotionDTO>>(){});
		Assert.assertEquals(2, promotions.size());
		assertTrue(promotions.stream().anyMatch(p->p.getPromotionId()==1L));
		assertTrue(promotions.stream().anyMatch(p->p.getPromotionId()==2L));
	}
	@Test
	public void retrievePromotionsForModelCode111() throws Exception {
		PromotionSearchDTO promotionSearch = new PromotionSearchDTO();
		Set<String> modelCodes = new HashSet<>();
		modelCodes.add("111");
		promotionSearch.setAffectedCompanyId(1L);
		promotionSearch.setModelCodes(modelCodes);
		ObjectMapper objectMapper = new ObjectMapper();
		String promotionSearchStr = objectMapper.writeValueAsString(promotionSearch);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/vzw/promotions")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(promotionSearchStr))
				.andExpect(status().isOk())
				.andReturn();
		List<PromotionDTO> promotions = objectMapper.readValue(result.getResponse().getContentAsString(),new TypeReference<List<PromotionDTO>>(){});
		Assert.assertEquals(2, promotions.size());
		assertTrue(promotions.stream().anyMatch(p->p.getPromotionId()==1L));
		assertTrue(promotions.stream().anyMatch(p->p.getPromotionId()==3L));
	}
	@Test
	public void retrievePromotionsWithQuestionResponse() throws Exception {
		PromotionSearchDTO promotionSearch = new PromotionSearchDTO();
		Set<String> modelCodes = new HashSet<>();
		modelCodes.add("222");
		promotionSearch.setAffectedCompanyId(1L);
		promotionSearch.setModelCodes(modelCodes);
		ObjectMapper objectMapper = new ObjectMapper();
		String promotionSearchStr = objectMapper.writeValueAsString(promotionSearch);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/vzw/promotions")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(promotionSearchStr))
				.andExpect(status().isOk())
				.andReturn();
		List<PromotionDTO> promotions = objectMapper.readValue(result.getResponse().getContentAsString(),new TypeReference<List<PromotionDTO>>(){});
		Assert.assertEquals(2, promotions.size());
		assertTrue(promotions.stream().anyMatch(p->p.getPromotionId()==1L));
		assertTrue(promotions.stream().anyMatch(p->p.getPromotionId()==4L));
	}
	@Test
	public void retrieveAvailablePromotions() throws Exception {
		PromotionSearchDTO promotionSearch = new PromotionSearchDTO();
		Set<String> modelCodes = new HashSet<>();
		modelCodes.add("222");
		promotionSearch.setAffectedCompanyId(1L);
		promotionSearch.setModelCodes(modelCodes);
		ObjectMapper objectMapper = new ObjectMapper();
		String promotionSearchStr = objectMapper.writeValueAsString(promotionSearch);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/vzw/activepromotions")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(promotionSearchStr))
				.andExpect(status().isOk())
				.andReturn();
		List<PromotionDTO> promotions = objectMapper.readValue(result.getResponse().getContentAsString(),new TypeReference<List<PromotionDTO>>(){});
		Assert.assertEquals(1, promotions.size());
		assertTrue(promotions.stream().anyMatch(p->p.getPromotionId()==4L));
	}
}
