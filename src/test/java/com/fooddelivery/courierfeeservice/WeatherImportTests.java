package com.fooddelivery.courierfeeservice;

import com.fooddelivery.courierfeeservice.repositories.weatherdata.WeatherDataRepository;
import com.fooddelivery.courierfeeservice.tasks.ScheduledWeatherImportTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
public class WeatherImportTests {
	private final ScheduledWeatherImportTask scheduledWeatherImportTask;
	private final WeatherDataRepository weatherDataRepository;

	@Autowired
    public WeatherImportTests(ScheduledWeatherImportTask scheduledWeatherImportTask, WeatherDataRepository weatherDataRepository) {
        this.scheduledWeatherImportTask = scheduledWeatherImportTask;
		this.weatherDataRepository = weatherDataRepository;
    }

    @Test
	void testWeatherImport() {
		scheduledWeatherImportTask.importWeatherData();
        assertFalse(weatherDataRepository.findAll().isEmpty());
	}

}
