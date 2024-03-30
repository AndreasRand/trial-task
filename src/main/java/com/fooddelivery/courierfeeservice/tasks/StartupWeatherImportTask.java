package com.fooddelivery.courierfeeservice.tasks;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class StartupWeatherImportTask {
    private final ScheduledWeatherImportTask scheduledWeatherImportTask;

    public StartupWeatherImportTask(ScheduledWeatherImportTask scheduledWeatherImportTask) {
        this.scheduledWeatherImportTask = scheduledWeatherImportTask;
    }

    @PostConstruct
    public void init() {
        scheduledWeatherImportTask.importWeatherData();
    }
}
