package io.spring.sample.dashboard;

import java.time.LocalDate;

import io.spring.sample.dashboard.stats.StatsService;
import io.spring.sample.dashboard.stats.support.GenerationStatisticsItem;
import reactor.core.publisher.Flux;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {

	private final StatsService statsService;

	public DashboardController(StatsService statsService) {
		this.statsService = statsService;
	}

	@GetMapping("/")
	public String index() {
		return redirect(LocalDate.now().minusWeeks(1).toString(), LocalDate.now().toString());
	}

	@PostMapping("/")
	public String redirect(@RequestParam String fromDate, @RequestParam String toDate) {
		return String.format("redirect:/stats/%s/%s", fromDate, toDate);
	}

	@GetMapping("/stats/{fromDate}/{toDate}")
	public String showDashboard(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable LocalDate toDate,
			Model model) {
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		model.addAttribute("stats", statsService.fetchStats(fromDate.toString(), toDate.toString()).block());
		return "index";
	}

	@GetMapping(path = "/live/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<GenerationStatisticsItem> liveStats() {
		return this.statsService.fetchLiveStats();
	}

}
