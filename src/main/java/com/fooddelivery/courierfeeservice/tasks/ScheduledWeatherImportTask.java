package com.fooddelivery.courierfeeservice.tasks;

import com.fooddelivery.courierfeeservice.entities.weatherdata.WeatherDataEntity;
import com.fooddelivery.courierfeeservice.models.Station;
import com.fooddelivery.courierfeeservice.models.WeatherDataResponse;
import com.fooddelivery.courierfeeservice.repositories.weatherdata.WeatherDataRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Component
public class ScheduledWeatherImportTask {
    private static final String WEATHER_DATA_API_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
    private static final List<Long> WMOCODE_LIST = Arrays.asList(26038L, 26242L, 41803L);
    private final WeatherDataRepository weatherDataRepository;

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
            System.out.println("There was an error with getting data from the website, database was not filled.");
            return;
        }

        // TODO optimize lookup, WMOCODE_LIST could be a map instead
        for (Station station : weatherDataResponse.getStationList().stream().filter(i -> WMOCODE_LIST.contains(i.getWMOCode())).toList()) {
            WeatherDataEntity weatherDataEntity = new WeatherDataEntity();
            weatherDataEntity.setStationName(station.getName());
            weatherDataEntity.setWmocode(station.getWMOCode());
            weatherDataEntity.setAirTemp(station.getAirTemp());
            weatherDataEntity.setWindSpeed(station.getWindSpeed());
            weatherDataEntity.setPhenomenon(station.getPhenomenon());
            weatherDataEntity.setTimestamp(weatherDataResponse.getTimestamp());
            weatherDataRepository.save(weatherDataEntity);
        }

        // TODO change to log4j
        System.out.println("Got weathers with timestamp: " + weatherDataResponse.getTimestamp());
    }

    private WeatherDataResponse unMarshalXml(InputStream inputStream) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(WeatherDataResponse.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (WeatherDataResponse) unmarshaller.unmarshal(inputStream);
    }
}
