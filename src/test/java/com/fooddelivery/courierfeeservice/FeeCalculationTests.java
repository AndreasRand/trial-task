package com.fooddelivery.courierfeeservice;

import com.fooddelivery.courierfeeservice.entities.rules.AirTemperatureExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.RegionalBaseFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WeatherPhenomenonExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WindSpeedExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.weatherdata.WeatherDataEntity;
import com.fooddelivery.courierfeeservice.models.CityType;
import com.fooddelivery.courierfeeservice.models.ConditionType;
import com.fooddelivery.courierfeeservice.models.VehicleType;
import com.fooddelivery.courierfeeservice.repositories.rules.AirTemperatureExtraFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.RegionalBaseFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.WeatherPhenomenonExtraFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.WindSpeedExtraFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.weatherdata.WeatherDataRepository;
import com.fooddelivery.courierfeeservice.services.FeeCalculationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class FeeCalculationTests {
    private final WeatherDataRepository weatherDataRepository;
    private final AirTemperatureExtraFeeRepository airTemperatureExtraFeeRepository;
    private final FeeCalculationService feeCalculationService;
    private final WindSpeedExtraFeeRepository windSpeedExtraFeeRepository;
    private final WeatherPhenomenonExtraFeeRepository weatherPhenomenonExtraFeeRepository;
    private static final long currentTimestamp = Instant.now().getEpochSecond();
    private static final long oldTimestamp = currentTimestamp - 10000;

    @Autowired
    public FeeCalculationTests(WeatherDataRepository weatherDataRepository, AirTemperatureExtraFeeRepository airTemperatureExtraFeeRepository, WindSpeedExtraFeeRepository windSpeedExtraFeeRepository, WeatherPhenomenonExtraFeeRepository weatherPhenomenonExtraFeeRepository, FeeCalculationService feeCalculationService, RegionalBaseFeeRepository regionalBaseFeeRepository) {
        this.weatherDataRepository = weatherDataRepository;
        this.airTemperatureExtraFeeRepository = airTemperatureExtraFeeRepository;
        this.windSpeedExtraFeeRepository = windSpeedExtraFeeRepository;
        this.weatherPhenomenonExtraFeeRepository = weatherPhenomenonExtraFeeRepository;
        this.feeCalculationService = feeCalculationService;
        regionalBaseFeeRepository.save(
                new RegionalBaseFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setCityType(CityType.TALLINN)
                        .setAmount(3)
                        .setTimestamp(oldTimestamp)
                        .build());
    }

    @Test
    @Rollback
    public void TestTempExtraFees() throws Exception {
        weatherDataRepository.save(
                new WeatherDataEntity.Builder()
                        .setStationName("TALLINN")
                        .setWmocode(CityType.TALLINN.getValue())
                        .setAirTemp(-1)
                        .setPhenomenon(null)
                        .setWindSpeed(0)
                        .setTimestamp(oldTimestamp)
                        .build());

        airTemperatureExtraFeeRepository.save(
                new AirTemperatureExtraFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setConditionType(ConditionType.FIRST_DEGREE)
                        .setTempRequirement(0)
                        .setAmount(0.7)
                        .setTimestamp(oldTimestamp)
                        .build());

        airTemperatureExtraFeeRepository.save(
                new AirTemperatureExtraFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setConditionType(ConditionType.FIRST_DEGREE)
                        .setTempRequirement(0)
                        .setAmount(0.5)
                        .setTimestamp(currentTimestamp)
                        .build());

        assertEquals(3.5, feeCalculationService.calculateTotalFee(CityType.TALLINN, VehicleType.BIKE, null));
        assertEquals(3.7, feeCalculationService.calculateTotalFee(CityType.TALLINN, VehicleType.BIKE, oldTimestamp));

        weatherDataRepository.save(
                new WeatherDataEntity.Builder()
                        .setStationName("TALLINN")
                        .setWmocode(CityType.TALLINN.getValue())
                        .setAirTemp(-11)
                        .setPhenomenon(null)
                        .setWindSpeed(0)
                        .setTimestamp(oldTimestamp)
                        .build());

        airTemperatureExtraFeeRepository.save(
                new AirTemperatureExtraFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setConditionType(ConditionType.SECOND_DEGREE)
                        .setTempRequirement(-10)
                        .setAmount(1.2)
                        .setTimestamp(oldTimestamp)
                        .build());

        airTemperatureExtraFeeRepository.save(
                new AirTemperatureExtraFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setConditionType(ConditionType.SECOND_DEGREE)
                        .setTempRequirement(-10)
                        .setAmount(1)
                        .setTimestamp(currentTimestamp)
                        .build());

        assertEquals(4, feeCalculationService.calculateTotalFee(CityType.TALLINN, VehicleType.BIKE, null));
        assertEquals(4.2, feeCalculationService.calculateTotalFee(CityType.TALLINN, VehicleType.BIKE, oldTimestamp));
    }

    @Test
    @Rollback
    public void TestWindExtraFees() throws Exception {
        weatherDataRepository.save(
                new WeatherDataEntity.Builder()
                        .setStationName("TALLINN")
                        .setWmocode(CityType.TALLINN.getValue())
                        .setAirTemp(10)
                        .setPhenomenon(null)
                        .setWindSpeed(11)
                        .setTimestamp(oldTimestamp)
                        .build());

        windSpeedExtraFeeRepository.save(
                new WindSpeedExtraFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setConditionType(ConditionType.FIRST_DEGREE)
                        .setWindSpeedRequirement(10)
                        .setAmount(0.7)
                        .setTimestamp(oldTimestamp)
                        .build());
        windSpeedExtraFeeRepository.save(
                new WindSpeedExtraFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setConditionType(ConditionType.FIRST_DEGREE)
                        .setWindSpeedRequirement(10)
                        .setAmount(0.5)
                        .setTimestamp(currentTimestamp)
                        .build());

        assertEquals(3.5, feeCalculationService.calculateTotalFee(CityType.TALLINN, VehicleType.BIKE, null));
        assertEquals(3.7, feeCalculationService.calculateTotalFee(CityType.TALLINN, VehicleType.BIKE, oldTimestamp));

        weatherDataRepository.save(
                new WeatherDataEntity.Builder()
                        .setStationName("TALLINN")
                        .setWmocode(CityType.TALLINN.getValue())
                        .setAirTemp(10)
                        .setPhenomenon(null)
                        .setWindSpeed(21)
                        .setTimestamp(oldTimestamp)
                        .build());

        windSpeedExtraFeeRepository.save(
                new WindSpeedExtraFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setConditionType(ConditionType.DISALLOWED)
                        .setWindSpeedRequirement(20)
                        .setAmount(1)
                        .setTimestamp(oldTimestamp)
                        .build());
        windSpeedExtraFeeRepository.save(
                new WindSpeedExtraFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setConditionType(ConditionType.DISALLOWED)
                        .setWindSpeedRequirement(20)
                        .setAmount(1.2)
                        .setTimestamp(currentTimestamp)
                        .build());

        assertThrows(Exception.class, () -> feeCalculationService.calculateTotalFee(CityType.TALLINN, VehicleType.BIKE, null));
        assertThrows(Exception.class, () -> feeCalculationService.calculateTotalFee(CityType.TALLINN, VehicleType.BIKE, oldTimestamp));
    }

    @Test
    @Rollback
    public void TestPhenomenonExtraFees() throws Exception {
        weatherDataRepository.save(
                new WeatherDataEntity.Builder()
                        .setStationName("TALLINN")
                        .setWmocode(CityType.TALLINN.getValue())
                        .setAirTemp(10)
                        .setPhenomenon("Light rain")
                        .setWindSpeed(0)
                        .setTimestamp(oldTimestamp)
                        .build());

        weatherPhenomenonExtraFeeRepository.save(
                new WeatherPhenomenonExtraFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setConditionType(ConditionType.FIRST_DEGREE)
                        .setPhenomenonPattern("(.*)rain(.*)")
                        .setAmount(0.7)
                        .setTimestamp(oldTimestamp)
                        .build());

        weatherPhenomenonExtraFeeRepository.save(
                new WeatherPhenomenonExtraFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setConditionType(ConditionType.FIRST_DEGREE)
                        .setPhenomenonPattern("(.*)rain(.*)")
                        .setAmount(0.5)
                        .setTimestamp(currentTimestamp)
                        .build());

        assertEquals(3.5, feeCalculationService.calculateTotalFee(CityType.TALLINN, VehicleType.BIKE, null));
        assertEquals(3.7, feeCalculationService.calculateTotalFee(CityType.TALLINN, VehicleType.BIKE, oldTimestamp));

        weatherDataRepository.save(
                new WeatherDataEntity.Builder()
                        .setStationName("TALLINN")
                        .setWmocode(CityType.TALLINN.getValue())
                        .setAirTemp(10)
                        .setPhenomenon("Light snowfall")
                        .setWindSpeed(0)
                        .setTimestamp(oldTimestamp)
                        .build());

        weatherPhenomenonExtraFeeRepository.save(
                new WeatherPhenomenonExtraFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setConditionType(ConditionType.SECOND_DEGREE)
                        .setPhenomenonPattern("(.*)(snow|sleet)(.*)")
                        .setAmount(1.2)
                        .setTimestamp(oldTimestamp)
                        .build());

        weatherPhenomenonExtraFeeRepository.save(
                new WeatherPhenomenonExtraFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setConditionType(ConditionType.SECOND_DEGREE)
                        .setPhenomenonPattern("(.*)(snow|sleet)(.*)")
                        .setAmount(1)
                        .setTimestamp(currentTimestamp)
                        .build());

        assertEquals(4, feeCalculationService.calculateTotalFee(CityType.TALLINN, VehicleType.BIKE, null));
        assertEquals(4.2, feeCalculationService.calculateTotalFee(CityType.TALLINN, VehicleType.BIKE, oldTimestamp));

        weatherDataRepository.save(
                new WeatherDataEntity.Builder()
                        .setStationName("TALLINN")
                        .setWmocode(CityType.TALLINN.getValue())
                        .setAirTemp(10)
                        .setPhenomenon("Thunderstorm")
                        .setWindSpeed(0)
                        .setTimestamp(oldTimestamp)
                        .build());

        weatherPhenomenonExtraFeeRepository.save(
                new WeatherPhenomenonExtraFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setConditionType(ConditionType.DISALLOWED)
                        .setPhenomenonPattern("(.*)(thunder|glaze|hail)(.*)")
                        .setAmount(1.2)
                        .setTimestamp(oldTimestamp)
                        .build());

        weatherPhenomenonExtraFeeRepository.save(
                new WeatherPhenomenonExtraFeeEntity.Builder()
                        .setVehicleType(VehicleType.BIKE)
                        .setConditionType(ConditionType.DISALLOWED)
                        .setPhenomenonPattern("(.*)(thunder|glaze|hail)(.*)")
                        .setAmount(1)
                        .setTimestamp(currentTimestamp)
                        .build());

        assertThrows(Exception.class, () -> feeCalculationService.calculateTotalFee(CityType.TALLINN, VehicleType.BIKE, null));
        assertThrows(Exception.class, () -> feeCalculationService.calculateTotalFee(CityType.TALLINN, VehicleType.BIKE, oldTimestamp));
    }
}
