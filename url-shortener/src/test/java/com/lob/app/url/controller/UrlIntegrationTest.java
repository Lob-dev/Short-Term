package com.lob.app.url.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.lob.app.url.TestIntegrationContext;
import com.lob.app.url.controller.form.UrlForm.Request;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class UrlIntegrationTest extends TestIntegrationContext {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;


	@Test
	void 주어지는_Target_URL이_동일한_경우_같은_SHORT_URL이_제공되어야_한다() throws Exception {

		Request.Create create = getUrlCreateRequest();

		String firstReturnValue = mockMvc.perform(post("/api/urls")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(create)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.shortUrl").exists())
				.andReturn().getResponse().getContentAsString();

		String secondReturnValue = mockMvc.perform(post("/api/urls")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(create)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.shortUrl").exists())
				.andReturn().getResponse().getContentAsString();

		String readFirst = JsonPath.read(firstReturnValue, "$.shortUrl");
		String readSecond = JsonPath.read(secondReturnValue, "$.shortUrl");
		assertEquals(readFirst, readSecond);
	}


	@RepeatedTest(1000)
	void 동일한_요청이_여러번_들어와도_Entity_무결성은_유지되어야_한다() throws Exception {

		Request.Create create = getUrlCreateRequest();

		mockMvc.perform(post("/api/urls")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(create)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.shortUrl").exists());
	}


	@Test
	void SHORT_URL의_길이는_8자리로_제공되어야_한다() throws Exception {

		Request.Create create = getUrlCreateRequest();

		String returnValue = mockMvc.perform(post("/api/urls")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(create)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.shortUrl").exists())
				.andReturn().getResponse().getContentAsString();

		String read = JsonPath.read(returnValue, "$.shortUrl");
		assertEquals(read.length(), 8);
	}


	@Test
	void SHORT_URL을_통해_요청할_경우_Target_URL으로_이동되어야_한다() throws Exception {

		Request.Create create = getUrlCreateRequest();

		mockMvc.perform(post("/api/urls")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(create)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.shortUrl").exists());

		mockMvc.perform(get("/api/3DtnlNC6")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("https://jojoldu.tistory.com/266"))
		;
	}


	@Test
	void Short_URL을_통한_요청_횟수는_저장되어야_한다() throws Exception {

		Request.Create create = getUrlCreateRequest();

		mockMvc.perform(post("/api/urls")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(create)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.shortUrl").exists());

		mockMvc.perform(get("/api/3DtnlNC6/count")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.shortUrl").exists())
				.andExpect(jsonPath("$.requestCount").exists());
	}


	@Test
	void 주어지는_Target_URL이_잘못된_형식일_경우_400을_반환하여야_한다() throws Exception {

		Request.Create create = getUrlCreateBadRequest();

		mockMvc.perform(post("/api/urls")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(create)))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
	}


	@Test
	void 주어지는_Short_URL이_8자리가_아닌_경우_400을_반환하여야_한다() throws Exception {

		mockMvc.perform(get("/api/3DtnlNC")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}


	@Test
	void 주어지는_Short_URL의_정보가_없는_경우_400을_반환하여야_한다() throws Exception {

		mockMvc.perform(get("/api/FFFFFFFF")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	private Request.Create getUrlCreateRequest() {
		return new Request.Create("https://jojoldu.tistory.com/266");
	}

	private Request.Create getUrlCreateBadRequest() {
		return new Request.Create("jojoldu.tistory");
	}

	private String toJson(Request.Create create) throws JsonProcessingException {
		return objectMapper.writeValueAsString(create);
	}
}