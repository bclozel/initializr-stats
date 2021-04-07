package io.spring.sample.dashboard;

import java.time.LocalDate;

import io.spring.sample.dashboard.stats.StatsContainer;
import io.spring.sample.dashboard.stats.StatsService;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DashboardController.class)
@WithMockUser
class DashboardControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private StatsService statsService;

	@Test
	void indexShouldRedirectToThisWeek() throws Exception {
		String today = LocalDate.now().toString();
		String aWeekAgo = LocalDate.now().minusDays(7).toString();
		this.mvc.perform(get("/")).andExpect(matchAll(status().is(302),
				header().string(HttpHeaders.LOCATION, String.format("/stats/%s/%s", aWeekAgo, today))));
	}

	@Test
	void showDashboardContainsStats() throws Exception {
		StatsContainer container = StatsContainer.range("2018-01-01", "2018-01-07").build();
		given(statsService.fetchStats("2018-01-01", "2018-01-07")).willReturn(Mono.just(container));
		this.mvc.perform(get("/stats/2018-01-01/2018-01-07")).andExpect(status().is(200))
				.andExpect(matchAll(status().isOk(),
						model().attribute("fromDate", LocalDate.of(2018, 1, 1)),
						model().attribute("toDate", LocalDate.of(2018, 1, 7)),
						model().attribute("stats", container)));
	}

}
