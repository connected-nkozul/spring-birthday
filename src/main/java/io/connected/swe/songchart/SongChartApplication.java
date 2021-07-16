package io.connected.swe.songchart;

import io.connected.swe.songchart.chart.ChartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SongChartApplication {

	Logger logger = LoggerFactory.getLogger(SongChartApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SongChartApplication.class, args);
	}
}
