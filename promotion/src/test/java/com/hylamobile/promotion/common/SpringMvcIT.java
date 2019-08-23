package com.hylamobile.promotion.common;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class SpringMvcIT extends SpringIT {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    protected MockMvc mockMvc;
    @Before
    public void setupMVCMockEnv(){
        //setup to skip spring security
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//				.apply(springSecurity())
                .build();
    }
}
