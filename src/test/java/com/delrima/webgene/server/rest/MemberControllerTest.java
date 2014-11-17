package com.delrima.webgene.server.rest;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileNotFoundException;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Log4jConfigurer;
import org.springframework.web.context.WebApplicationContext;

import com.delrima.webgene.server.configuration.ContainerConfiguration;
import com.delrima.webgene.server.configuration.SpringRestConfiguration;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ContainerConfiguration.class, SpringRestConfiguration.class })
public class MemberControllerTest {

	static {
		try {
			Log4jConfigurer.initLogging("classpath:log4j.properties");
		} catch (final FileNotFoundException ex) {
			System.err.println("Cannot Initialize log4j");
		}
	}

	@Resource
	private WebApplicationContext webApplicationContext;
	protected MockMvc mockMvc;

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void test() throws Exception {
		mockMvc.perform(post("/member").contentType(APPLICATION_JSON)
										.content(	"{\"firstname\" : \"Bhavesh\", \"lastname\" : \"Thakker\", \"gender\" : \"M\", \"fatherid\" : 0, \"motherid\" : 0, \"spouseid\" : 0}"))
				.andExpect(status().isOk());
		// retrieve all
		mockMvc.perform(get("/member")).andExpect(status().isOk()).andExpect(jsonPath("$[0].firstname", equalToIgnoringCase("Bhavesh")));
	}

}
