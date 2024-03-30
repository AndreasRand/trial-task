package com.fooddelivery.courierfeeservice;

import com.fooddelivery.courierfeeservice.tasks.ScheduledWeatherImportTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CourierfeeserviceApplicationTests {
	@Autowired
	ScheduledWeatherImportTask scheduledWeatherImportTask;

    @Test
	void contextLoads() {
	}

	@Test
	@Rollback()
	public void testAPIRequest() {
		try {
			scheduledWeatherImportTask.importWeatherData();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

}
