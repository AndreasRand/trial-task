package com.fooddelivery.courierfeeservice.services;

import com.fooddelivery.courierfeeservice.entities.rules.AirTemperatureExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.RegionalBaseFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WeatherPhenomenonExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WindSpeedExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.weatherdata.WeatherDataEntity;
import com.fooddelivery.courierfeeservice.models.rules.CityType;
import com.fooddelivery.courierfeeservice.models.rules.ConditionType;
import com.fooddelivery.courierfeeservice.models.apiresponse.TotalFee;
import com.fooddelivery.courierfeeservice.models.rules.VehicleType;
import com.fooddelivery.courierfeeservice.repositories.rules.AirTemperatureExtraFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.RegionalBaseFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.WeatherPhenomenonExtraFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.rules.WindSpeedExtraFeeRepository;
import com.fooddelivery.courierfeeservice.repositories.weatherdata.WeatherDataRepository;
import com.fooddelivery.courierfeeservice.controller.exceptions.ApiExceptionErrorType;
import com.fooddelivery.courierfeeservice.controller.exceptions.ApiException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.fooddelivery.courierfeeservice.controller.exceptions.ApiExceptionErrorMessage.DISALLOWED_WEATHER_PHENOMENON_ERROR_MESSAGE;
import static com.fooddelivery.courierfeeservice.controller.exceptions.ApiExceptionErrorMessage.DISALLOWED_WIND_SPEED_ERROR_MESSAGE;
import static com.fooddelivery.courierfeeservice.controller.exceptions.ApiExceptionErrorMessage.REGIONAL_BASE_FEE_ABSENT_ERROR_MESSAGE;
import static com.fooddelivery.courierfeeservice.controller.exceptions.ApiExceptionErrorMessage.WEATHER_DATA_ABSENT_ERROR_MESSAGE;

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

    public TotalFee calculateTotalFee(CityType cityType, VehicleType vehicleType, Long timestamp) throws ApiException {
        WeatherDataEntity weatherDataEntity;
        if (timestamp == null) weatherDataEntity = weatherDataRepository.findFirstByWmocodeOrderByIdDesc(cityType.getValue()).orElse(null);
        else weatherDataEntity = weatherDataRepository.findFirstByWmocodeAndTimestampLessThanEqualOrderByIdDesc(cityType.getValue(), timestamp).orElse(null);

        if (weatherDataEntity == null) throw new ApiException(WEATHER_DATA_ABSENT_ERROR_MESSAGE, ApiExceptionErrorType.WEATHER_DATA_ABSENT);

        double RBF = calculateRBF(vehicleType, cityType, timestamp);
        double ATEF = calculateATEF(weatherDataEntity, vehicleType, timestamp);
        double WSEF = calculateWSEF(weatherDataEntity, vehicleType, timestamp);
        double WPEF = calculateWPEF(weatherDataEntity, vehicleType, timestamp);

        return new TotalFee(RBF + ATEF + WSEF + WPEF);
    }

    private double calculateRBF(VehicleType vehicleType, CityType cityType, Long timestamp) throws ApiException {
        Optional<RegionalBaseFeeEntity> regionalBaseFeeEntity;
        if (timestamp == null) regionalBaseFeeEntity = regionalBaseFeeRepository.findTopByVehicleTypeAndCityTypeOrderByIdDesc(vehicleType, cityType);
        else regionalBaseFeeEntity = regionalBaseFeeRepository.findTopByVehicleTypeAndCityTypeAndTimestampLessThanEqualOrderByTimestampDesc(vehicleType, cityType, timestamp);

        if (regionalBaseFeeEntity.isEmpty()) throw new ApiException(REGIONAL_BASE_FEE_ABSENT_ERROR_MESSAGE, ApiExceptionErrorType.REGIONAL_BASE_FEE_ABSENT);

        return regionalBaseFeeEntity.get().getAmount();
    }

    private double calculateATEF(WeatherDataEntity weatherDataEntity, VehicleType vehicleType, Long timestamp) {
        Optional<AirTemperatureExtraFeeEntity> airTemperatureExtraFeeEntity;
        if (timestamp == null) airTemperatureExtraFeeEntity = airTemperatureExtraFeeRepository.findLatestByAirTemperatureAndVehicleType(weatherDataEntity.getAirTemp(), vehicleType);
        else airTemperatureExtraFeeEntity = airTemperatureExtraFeeRepository.findByAirTemperatureAndVehicleTypeAndTimestamp(weatherDataEntity.getAirTemp(), vehicleType, timestamp);

        if (airTemperatureExtraFeeEntity.isEmpty()) return DEFAULT_VALUE;

        return airTemperatureExtraFeeEntity.get().getAmount();
    }

    private double calculateWSEF(WeatherDataEntity weatherDataEntity, VehicleType vehicleType, Long timestamp) throws ApiException {
        WindSpeedExtraFeeEntity windSpeedExtraFeeEntity;

        if (timestamp == null) windSpeedExtraFeeEntity = windSpeedExtraFeeRepository.findLatestByWindSpeedAndVehicleType(weatherDataEntity.getWindSpeed(), vehicleType).orElse(null);
        else windSpeedExtraFeeEntity = windSpeedExtraFeeRepository.findByWindSpeedAndVehicleTypeAndTimestamp(weatherDataEntity.getWindSpeed(), vehicleType, timestamp).orElse(null);

        if (windSpeedExtraFeeEntity == null) return DEFAULT_VALUE;

        if (ConditionType.DISALLOWED.equals(windSpeedExtraFeeEntity.getConditionType())) throw new ApiException(DISALLOWED_WIND_SPEED_ERROR_MESSAGE, ApiExceptionErrorType.DISALLOWED_WIND_SPEED);
        return windSpeedExtraFeeEntity.getAmount();
    }

    private double calculateWPEF(WeatherDataEntity weatherDataEntity, VehicleType vehicleType, Long timestamp) throws ApiException {
        String phenomenon = weatherDataEntity.getPhenomenon();

        if (phenomenon == null || phenomenon.isEmpty()) return DEFAULT_VALUE;

        phenomenon = phenomenon.toLowerCase();

        WeatherPhenomenonExtraFeeEntity weatherPhenomenonExtraFeeEntity;
        if (timestamp == null) weatherPhenomenonExtraFeeEntity = weatherPhenomenonExtraFeeRepository.findLatestByPhenomenonAndVehicleType(phenomenon, vehicleType).orElse(null);
        else weatherPhenomenonExtraFeeEntity = weatherPhenomenonExtraFeeRepository.findLatestByPhenomenonAndVehicleTypeAndTimestamp(phenomenon, vehicleType, timestamp).orElse(null);

        if (weatherPhenomenonExtraFeeEntity == null) return DEFAULT_VALUE;

        if (ConditionType.DISALLOWED.equals(weatherPhenomenonExtraFeeEntity.getConditionType())) throw new ApiException(DISALLOWED_WEATHER_PHENOMENON_ERROR_MESSAGE, ApiExceptionErrorType.DISALLOWED_WEATHER_PHENOMENON);
        return weatherPhenomenonExtraFeeEntity.getAmount();

    }
}
