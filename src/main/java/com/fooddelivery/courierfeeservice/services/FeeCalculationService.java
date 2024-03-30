package com.fooddelivery.courierfeeservice.services;

import com.fooddelivery.courierfeeservice.entities.rules.AirTemperatureExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.RegionalBaseFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WeatherPhenomenonExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WindSpeedExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.weatherdata.WeatherDataEntity;
import com.fooddelivery.courierfeeservice.models.CityType;
import com.fooddelivery.courierfeeservice.models.ConditionType;
import com.fooddelivery.courierfeeservice.models.VehicleType;
import com.fooddelivery.courierfeeservice.repositories.weatherdata.WeatherDataRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.AirTemperatureExtraFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.RegionalBaseFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.WeatherPhenomenonExtraFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.WindSpeedExtraFeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeeCalculationService {
    private final WeatherDataRepository weatherDataRepository;
    private final RegionalBaseFeeRepository regionalBaseFeeRepository;
    private final WeatherPhenomenonExtraFeeRepository weatherPhenomenonExtraFeeRepository;
    private final WindSpeedExtraFeeRepository windSpeedExtraFeeRepository;
    private final AirTemperatureExtraFeeRepository airTemperatureExtraFeeRepository;
    private static final double DEFAULT_VALUE = 0.0;

    public FeeCalculationService(WeatherDataRepository weatherDataRepository, RegionalBaseFeeRepository regionalBaseFeeRepository, WeatherPhenomenonExtraFeeRepository weatherPhenomenonExtraFeeRepository, WindSpeedExtraFeeRepository windSpeedExtraFeeRepository, AirTemperatureExtraFeeRepository airTemperatureExtraFeeRepository) {
        this.weatherDataRepository = weatherDataRepository;
        this.regionalBaseFeeRepository = regionalBaseFeeRepository;
        this.weatherPhenomenonExtraFeeRepository = weatherPhenomenonExtraFeeRepository;
        this.windSpeedExtraFeeRepository = windSpeedExtraFeeRepository;
        this.airTemperatureExtraFeeRepository = airTemperatureExtraFeeRepository;
    }

    public double calculateTotalFee(CityType cityType, VehicleType vehicleType, Long timestamp) throws Exception {
        WeatherDataEntity weatherDataEntity;
        if (timestamp == null) weatherDataEntity = weatherDataRepository.findFirstByWmocodeOrderByIdDesc(cityType.getValue()).orElse(null);
        else weatherDataEntity = weatherDataRepository.findFirstByWmocodeAndTimestampLessThanEqualOrderByIdDesc(cityType.getValue(), timestamp).orElse(null);

        if (weatherDataEntity == null) throw new Exception();

        double RBF = calculateRBF(vehicleType, cityType, timestamp);
        double ATEF = calculateATEF(weatherDataEntity, vehicleType, timestamp);
        double WSEF = calculateWSEF(weatherDataEntity, vehicleType, timestamp);
        double WPEF = calculateWPEF(weatherDataEntity, vehicleType, timestamp);

        return RBF + ATEF + WSEF + WPEF;
    }

    private double calculateRBF(VehicleType vehicleType, CityType cityType, Long timestamp) throws Exception {
        Optional<RegionalBaseFeeEntity> regionalBaseFeeEntity;
        if (timestamp == null) regionalBaseFeeEntity = regionalBaseFeeRepository.findTopByVehicleTypeAndCityTypeOrderByIdDesc(vehicleType, cityType);
        else regionalBaseFeeEntity = regionalBaseFeeRepository.findTopByVehicleTypeAndCityTypeAndTimestampLessThanEqualOrderByTimestampDesc(vehicleType, cityType, timestamp);

        if (regionalBaseFeeEntity.isEmpty()) throw new Exception();

        return regionalBaseFeeEntity.get().getAmount();
    }

    private double calculateATEF(WeatherDataEntity weatherDataEntity, VehicleType vehicleType, Long timestamp) {
        AirTemperatureExtraFeeEntity airTemperatureExtraFeeEntity;
        if (timestamp == null) airTemperatureExtraFeeEntity = airTemperatureExtraFeeRepository.findLatestByAirTemperatureAndVehicleType(weatherDataEntity.getAirTemp(), vehicleType).orElse(new AirTemperatureExtraFeeEntity());
        else airTemperatureExtraFeeEntity = airTemperatureExtraFeeRepository.findByAirTemperatureAndVehicleTypeAndTimestamp(weatherDataEntity.getAirTemp(), vehicleType, timestamp).orElse(new AirTemperatureExtraFeeEntity());

        return airTemperatureExtraFeeEntity.getAmount();
    }

    private double calculateWSEF(WeatherDataEntity weatherDataEntity, VehicleType vehicleType, Long timestamp) throws Exception {
        WindSpeedExtraFeeEntity windSpeedExtraFeeEntity;

        if (timestamp == null) windSpeedExtraFeeEntity = windSpeedExtraFeeRepository.findLatestByWindSpeedAndVehicleType(weatherDataEntity.getWindSpeed(), vehicleType).orElse(new WindSpeedExtraFeeEntity());
        else windSpeedExtraFeeEntity = windSpeedExtraFeeRepository.findByWindSpeedAndVehicleTypeAndTimestamp(weatherDataEntity.getWindSpeed(), vehicleType, timestamp).orElse(new WindSpeedExtraFeeEntity());

        if (ConditionType.DISALLOWED.equals(windSpeedExtraFeeEntity.getConditionType())) throw new Exception();
        return windSpeedExtraFeeEntity.getAmount();
    }

    private double calculateWPEF(WeatherDataEntity weatherDataEntity, VehicleType vehicleType, Long timestamp) throws Exception {
        String phenomenon = weatherDataEntity.getPhenomenon();

        if (phenomenon == null || phenomenon.isEmpty()) return DEFAULT_VALUE;

        phenomenon = phenomenon.toLowerCase();

        WeatherPhenomenonExtraFeeEntity weatherPhenomenonExtraFeeEntity;
        if (timestamp == null) weatherPhenomenonExtraFeeEntity = weatherPhenomenonExtraFeeRepository.findLatestByPhenomenonAndVehicleType(phenomenon, vehicleType).orElse(new WeatherPhenomenonExtraFeeEntity());
        else weatherPhenomenonExtraFeeEntity = weatherPhenomenonExtraFeeRepository.findLatestByPhenomenonAndVehicleTypeAndTimestamp(phenomenon, vehicleType, timestamp).orElse(new WeatherPhenomenonExtraFeeEntity());

        if (ConditionType.DISALLOWED.equals(weatherPhenomenonExtraFeeEntity.getConditionType())) throw new Exception();
        return weatherPhenomenonExtraFeeEntity.getAmount();

    }
}
