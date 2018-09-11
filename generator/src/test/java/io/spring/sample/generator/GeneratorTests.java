package io.spring.sample.generator;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GeneratorTests {

	private final DataSet data2016 = sampleDataSet(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 31));

	private final DataSet data2017 = sampleDataSet(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 12, 31));

	private final DataSet data2018 = sampleDataSet(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31));

	private final Release sb15 = release(LocalDate.of(2017, 5, 1), LocalDate.of(2018, 3, 1), "1.5.x");

	private final Release sb20 = release(LocalDate.of(2018, 3, 1), LocalDate.of(2018, 10, 1), "2.0.x");

	private final Event event2016 = new Event("Test", LocalDate.of(2016, 5, 1), Event.Type.RELEASE);

	private final Event event2017 = new Event("Test", LocalDate.of(2017, 6, 1), Event.Type.RELEASE);

	private final Generator metadata = new Generator(
			Arrays.asList(data2016, data2017, data2018),
			Arrays.asList(sb15, sb20),
			Arrays.asList(event2016, event2017));


	@Test
	public void getDataSetsNoMatch() {
		assertThat(metadata.getDataSets(new DateRange(LocalDate.of(2013, 1, 5),
				LocalDate.of(2014, 5, 12)))).hasSize(0);
	}

	@Test
	public void getDataSetsSingleRangeMatch() {
		assertThat(metadata.getDataSets(new DateRange(LocalDate.of(2016, 1, 5),
				LocalDate.of(2016, 5, 12)))).containsExactly(data2016);
	}

	@Test
	public void getDataSetsSingleRangeMatchExactFrom() {
		assertThat(metadata.getDataSets(new DateRange(LocalDate.of(2016, 1, 1),
				LocalDate.of(2016, 5, 12)))).containsExactly(data2016);
	}

	@Test
	public void getDataSetsSingleRangeMatchExactTo() {
		assertThat(metadata.getDataSets(new DateRange(LocalDate.of(2016, 5, 1),
				LocalDate.of(2016, 12, 31)))).containsExactly(data2016);
	}

	@Test
	public void getDataSetsMultipleRangesMatchExactFrom() {
		assertThat(metadata.getDataSets(new DateRange(LocalDate.of(2016, 1, 1),
				LocalDate.of(2018, 5, 12)))).containsExactly(data2016, data2017, data2018);
	}

	@Test
	public void getDataSetsMultipleRangesMatchExactTo() {
		assertThat(metadata.getDataSets(new DateRange(LocalDate.of(2016, 5, 12),
				LocalDate.of(2017, 12, 31)))).containsExactly(data2016, data2017);
	}

	@Test
	public void getEventsNoMatch() {
		assertThat(metadata.getEvents(new DateRange(LocalDate.of(2018, 1, 1),
				LocalDate.of(2018, 2, 2)))).hasSize(0);
	}

	@Test
	public void getEventsSingleMatchExactDay() {
		assertThat(metadata.getEvents(new DateRange(LocalDate.of(2016, 5, 1),
				LocalDate.of(2016, 5, 1)))).containsExactly(event2016);
	}

	@Test
	public void getEventsSingleMatch() {
		assertThat(metadata.getEvents(new DateRange(LocalDate.of(2016, 1, 1),
				LocalDate.of(2016, 7, 1)))).containsExactly(event2016);
	}

	@Test
	public void getEventsMultipleMatches() {
		assertThat(metadata.getEvents(new DateRange(LocalDate.of(2016, 1, 1),
				LocalDate.of(2018, 7, 1)))).containsExactly(event2016, event2017);
	}

	@Test
	public void generateSingleRange() {
		GenerationStatistics statistics = metadata.generateStatistics(
				new DateRange(LocalDate.of(2017, 5, 1), LocalDate.of(2017, 5, 3)));
		assertThat(statistics).isNotNull();
		assertThat(statistics.getRange().getFrom()).isEqualTo(LocalDate.of(2017, 5, 1));
		assertThat(statistics.getRange().getTo()).isEqualTo(LocalDate.of(2017, 5, 3));
		assertThat(statistics.getEntries()).containsOnlyKeys("1.5.x");
		List<GenerationStatistics.Entry> entries = statistics.getEntries().get("1.5.x");
		assertThat(entries).hasSize(3);
		assertThat(entries.get(0).getDate()).isEqualTo(LocalDate.of(2017, 5, 1));
		assertThat(entries.get(0).getProjectsCount()).isGreaterThan(0);
		assertThat(entries.get(1).getDate()).isEqualTo(LocalDate.of(2017, 5, 2));
		assertThat(entries.get(1).getProjectsCount()).isGreaterThan(0);
		assertThat(entries.get(2).getDate()).isEqualTo(LocalDate.of(2017, 5, 3));
		assertThat(entries.get(2).getProjectsCount()).isGreaterThan(0);
	}

	private static DataSet sampleDataSet(LocalDate from, LocalDate to) {
		DataSet dataSet = new DataSet();
		dataSet.setRange(new DateRange(from, to));
		dataSet.getData().setMonday(1000);
		dataSet.getData().setTuesday(1200);
		dataSet.getData().setWednesday(1200);
		dataSet.getData().setThursday(1400);
		dataSet.getData().setFriday(900);
		dataSet.getData().setSaturday(500);
		dataSet.getData().setSunday(100);
		return dataSet;
	}

	private static Release release(LocalDate from, LocalDate to, String current) {
		Release release = new Release();
		release.setRange(new DateRange(from, to));
		release.getData().setCurrent(current);
		return release;
	}

}
