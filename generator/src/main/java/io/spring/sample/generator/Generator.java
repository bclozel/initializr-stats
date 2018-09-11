package io.spring.sample.generator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import reactor.core.publisher.Flux;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class Generator {

	private static final Random random = new Random();

	private final List<DataSet> dataSets;

	private final List<Release> releases;

	private final List<Event> events;

	private Latency latency;

	public Generator(List<DataSet> dataSets,
			List<Release> releases, List<Event> events) {
		this.dataSets = new ArrayList<>(dataSets);
		this.releases = new ArrayList<>(releases);
		this.events = new ArrayList<>(events);
		this.latency = new Latency();
	}

	public GenerationStatistics generateStatistics(DateRange range) {
		return new RandomGenerator(range).generate();
	}

	public Flux<GenerationStatisticsItem> generateLiveStatistics(Duration periodicity) {
		return Flux.interval(Duration.ZERO, periodicity)
				.map(i -> generateCurrentTimestamp(periodicity)).map(timestamp -> {
					LocalDate date = timestamp.toLocalDate();
					RandomGenerator generator = new RandomGenerator(
							new DateRange(date, date));
					return generator.generateItem(timestamp, periodicity);
				}).share();
	}

	private LocalDateTime generateCurrentTimestamp(Duration periodicity) {
		LocalDateTime now = LocalDateTime.now();
		int periodicitySeconds = (int) periodicity.getSeconds();
		return now.withSecond((now.getSecond() / periodicitySeconds) * periodicitySeconds)
				.withNano(0);
	}

	public List<DataSet> getDataSets(DateRange dateRange) {
		return this.dataSets.stream()
				.filter(dataSetMatch(dateRange))
				.collect(Collectors.toList());
	}

	public List<Release> getReleases(DateRange dateRange) {
		return this.releases.stream()
				.filter(releaseMatch(dateRange))
				.collect(Collectors.toList());
	}

	public List<Event> getEvents(DateRange dateRange) {
		return this.events.stream()
				.filter(eventMatch(dateRange))
				.collect(Collectors.toList());
	}

	public List<GeneratorClient> getTopIps(DateRange dateRange) {
		return IntStream.range(0, 10).mapToObj((i) -> new GeneratorClient(randomIp()))
				.collect(Collectors.toList());
	}

	public Latency getLatency() {
		return this.latency;
	}

	public void updateLatency(Float ratio, Integer latencyMin, Integer latencyMax) {
		float newRatio = (ratio != null ? ratio : this.latency.ratio);
		int newMin = (latencyMin != null ? latencyMin : this.latency.latencyMin);
		int newMax = (latencyMax != null ? latencyMax : this.latency.latencyMax);
		this.latency = new Latency(newRatio, newMin, newMax);
	}

	private String randomIp() {
		StringBuilder sb = new StringBuilder("10.");
		sb.append(random.nextInt(255)).append(".")
				.append(random.nextInt(255)).append(".")
				.append(random.nextInt(255));
		return sb.toString();
	}

	private MultiValueMap<LocalDate, Event> indexEvents(DateRange range) {
		MultiValueMap<LocalDate, Event> events = new LinkedMultiValueMap<>();
		getEvents(range).forEach((event) -> events.add(event.getDate(), event));
		return events;
	}

	private Predicate<DataSet> dataSetMatch(DateRange range) {
		return (dataSet) -> dataSet.getRange().match(range);
	}

	private Predicate<Release> releaseMatch(DateRange range) {
		return (release) -> release.getRange().match(range);
	}

	private Predicate<Event> eventMatch(DateRange range) {
		return (event) -> range.match(event.getDate());
	}

	private class RandomGenerator {

		private final DateRange range;

		private final List<Release> releases;

		private final List<DataSet> dataSets;

		private final MultiValueMap<LocalDate, Event> events;

		RandomGenerator(DateRange range) {
			this.range = range;
			this.releases = getReleases(range);
			this.dataSets = getDataSets(range);
			this.events = indexEvents(range);
			if (releases.isEmpty() || dataSets.isEmpty()) {
				throw new IllegalArgumentException("No available information for range " + range);
			}
		}

		GenerationStatistics generate() {
			Iterator<Release> releasesIt = releases.iterator();
			Release currentRelease = releasesIt.next();
			Iterator<DataSet> dataSetsIt = dataSets.iterator();
			DataSet currentDataSet = dataSetsIt.next();
			MultiValueMap<String, GenerationStatistics.Entry> entries = new LinkedMultiValueMap<>();
			LocalDate currentDay = range.getFrom();
			while (!currentDay.isAfter(range.getTo())) {
				if (!currentRelease.getRange().match(currentDay)) {
					if (!releasesIt.hasNext()) {
						throw new IllegalArgumentException("No release information for " + currentDay);
					}
					currentRelease = releasesIt.next();
				}
				if (!currentDataSet.getRange().match(currentDay)) {
					if (!dataSetsIt.hasNext()) {
						throw new IllegalArgumentException("No data information for " + currentDay);
					}
					currentDataSet = dataSetsIt.next();
				}
				int total = currentDataSet.getData().get(currentDay.getDayOfWeek());
				String next = currentRelease.getData().getNext();
				double currentRatio = (next != null ? 0.9 : 0.92);
				entries.add(currentRelease.getData().getCurrent(), randomValueForDay(currentDay, total, currentRatio));
				if (currentRelease.getData().getMaintenance() != null) {
					double maintenanceRatio = (next != null ? 0.08 : 0.1);
					entries.add(currentRelease.getData().getMaintenance(), randomValueForDay(currentDay, total, maintenanceRatio));
				}
				if (currentRelease.getData().getNext() != null) {
					double nextRatio = (next != null ? 0.02 : 0);
					entries.add(next, randomValueForDay(currentDay, total, nextRatio));
				}
				currentDay = currentDay.plusDays(1);
			}
			return new GenerationStatistics(range, entries);
		}

		GenerationStatisticsItem generateItem(LocalDateTime timestamp, Duration periodicity) {
			if (timestamp.isBefore(this.range.getFrom().atStartOfDay())
					|| timestamp.isAfter(this.range.getTo().atTime(LocalTime.MAX))) {
				throw new IllegalArgumentException(String.format(
						"Timestamp %s not within range %s", timestamp, this.range));
			}
			int dayTotal = dataSets.get(0).getData().get(timestamp.getDayOfWeek());
			double ratio = (double) periodicity.toMillis() / Duration.ofDays(1).toMillis();
			int total = (int) Math.ceil(dayTotal * ratio);
			DateTimeRange range = new DateTimeRange(timestamp,
					timestamp.plus(periodicity.getSeconds(), ChronoUnit.SECONDS));
			return new GenerationStatisticsItem(range, randomValueForPeriodicity(timestamp, total));
		}

		private int randomValueForPeriodicity(LocalDateTime timestamp, int total) {
			int ratio = 1;
			int hour = timestamp.getHour();
			if (hour > 8 && hour < 18) {
				double workingHoursRatio = (random.nextFloat() < 0.4  ? 2 : 1);
				ratio *= workingHoursRatio;
			}
			return random.nextInt(total * ratio);
		}

		// very scientific measure
		private GenerationStatistics.Entry randomValueForDay(LocalDate day, int total, double ratio) {
			double value = ratio * total;
			value = value - (value * 0.05);
			value = value + (value * random.nextFloat() / 10);
			return new GenerationStatistics.Entry(day, applyEvents(day, (long) value));
		}

		private long applyEvents(LocalDate date, long value) {
			List<Event> dateEvents = this.events.get(date);
			if (dateEvents != null) {
				long original = value;
				for (Event event : dateEvents) {
					value = event.getType().transformValue(original);
				}
			}
			return value;
		}

	}

	public static final class Latency {

		private final float ratio;

		private final int latencyMin;

		private final int latencyMax;

		Latency(float ratio, int latencyMin, int latencyMax) {
			this.ratio = ratio;
			this.latencyMin = latencyMin;
			this.latencyMax = latencyMax;
		}

		Latency() {
			this(0.0f, 200, 8000);
		}

		public Duration randomLatency() {
			boolean apply = random.nextFloat() < ratio;
			return apply ? generateRandomLatency() : Duration.ZERO;
		}

		public float getRatio() {
			return this.ratio;
		}

		public int getLatencyMin() {
			return this.latencyMin;
		}

		public int getLatencyMax() {
			return this.latencyMax;
		}

		private Duration generateRandomLatency() {
			int ms = random.nextInt(latencyMax - latencyMin) + latencyMin;
			return Duration.ofMillis(ms);
		}

	}

}
