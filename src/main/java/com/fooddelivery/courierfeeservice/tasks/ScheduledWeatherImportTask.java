package com.fooddelivery.courierfeeservice.tasks;

import com.fooddelivery.courierfeeservice.entities.weatherdata.WeatherDataEntity;
import com.fooddelivery.courierfeeservice.models.weatherdata.Station;
import com.fooddelivery.courierfeeservice.models.weatherdata.WeatherDataResponse;
import com.fooddelivery.courierfeeservice.repositories.weatherdata.WeatherDataRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Component
public class ScheduledWeatherImportTask {
    private static String WEATHER_DATA_API_URL;
    private static final Set<Long> WMOCODE_SET = new HashSet<>(Arrays.asList(26038L, 26242L, 41803L));
    private final WeatherDataRepository weatherDataRepository;
    private static final Logger logger = Logger.getLogger(ScheduledWeatherImportTask.class.getName());

    @Value("${weather_data_api}")
    public void setWEATHER_DATA_API_URL(String value) {
        WEATHER_DATA_API_URL = value;
    }

    @Autowired
    public ScheduledWeatherImportTask(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    @Scheduled(cron = "0 15 * * * *")
    public void importWeatherData() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(WEATHER_DATA_API_URL)).build();

        WeatherDataResponse weatherDataResponse;

        try {
            HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

            weatherDataResponse = unMarshalXml(response.body());
        } catch (Exception e) {
            logger.severe("There was an error with getting data from the website");
            return;
        }

        for (Station station : weatherDataResponse.getStationList().stream().filter(i -> WMOCODE_SET.contains(i.getWMOCode())).toList()) {
            weatherDataRepository.save(
                    new WeatherDataEntity.Builder()
                            .setStationName(station.getName())
                            .setWmocode(station.getWMOCode())
                            .setAirTemp(station.getAirTemp())
                            .setWindSpeed(station.getWindSpeed())
                            .setPhenomenon(station.getPhenomenon())
                            .setTimestamp(weatherDataResponse.getTimestamp())
                            .build());
        }

        logger.info("Got weathers with timestamp: " + weatherDataResponse.getTimestamp());
    }

    private WeatherDataResponse unMarshalXml(InputStream inputStream) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(WeatherDataResponse.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (WeatherDataResponse) unmarshaller.unmarshal(inputStream);
    }
}
