package io.spring.sample.dashboard;


import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DashboardControllerTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void indexShouldRedirectToThisWeek() throws Exception {
		String today = LocalDate.now().toString();
		String aWeekAgo = LocalDate.now().minusDays(7).toString();
		this.mvc.perform(get("/")).andExpect(status().is(302))
				.andExpect(header().string(HttpHeaders.LOCATION, String.format("/stats/%s/%s", aWeekAgo, today)));
	}
}
