package com.fooddelivery.courierfeeservice;

import com.fooddelivery.courierfeeservice.controller.exceptions.ApiExceptionErrorType;
import com.fooddelivery.courierfeeservice.entities.rules.AirTemperatureExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.RegionalBaseFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WeatherPhenomenonExtraFeeEntity;
import com.fooddelivery.courierfeeservice.entities.rules.WindSpeedExtraFeeEntity;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ApiInsertionEndpointTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Rollback
    public void testATEFEndpoint() throws Exception {
        AirTemperatureExtraFeeEntity entity = new AirTemperatureExtraFeeEntity();
        String validRequestData =
                "{\"vehicleType\": \"SCOOTER\", \"conditionType\": \"FIRST_DEGREE\", \"minTempRequirement\": -30, \"maxTempRequirement\":-10, \"amount\": 10}";
        String invalidRequestData =
                "{\"vehicleType\": \"BOAT\", \"conditionType\": \"FIRST_DEGREE\", \"minTempRequirement\": -30, \"maxTempRequirement\":-10, \"amount\": 10}";
        String missingRequestData =
                "{\"vehicleType\": \"SCOOTER\", \"conditionType\": \"FIRST_DEGREE\", \"minTempRequirement\": -30, \"maxTempRequirement\":-10}";
        String overLappingRequestData =
                "{\"vehicleType\": \"SCOOTER\", \"conditionType\": \"SECOND_DEGREE\", \"maxTempRequirement\":-20, \"amount\": 10}";
        String unOrderedRequestData =
                "{\"vehicleType\": \"SCOOTER\", \"conditionType\": \"SECOND_DEGREE\", \"maxTempRequirement\":0, \"minTempRequirement\":-9, \"amount\": 10}";

        mockMvc.perform(post("/api/ATEF").contentType(MediaType.APPLICATION_JSON).content(validRequestData))
                .andExpect(status().isOk()).andDo(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    entity.setId(((Integer) JsonPath.read(jsonResponse, "$.id")).longValue());
                });
        mockMvc.perform(post("/api/ATEF").contentType(MediaType.APPLICATION_JSON).content(invalidRequestData)).andExpect(status().isBadRequest());
        mockMvc.perform(post("/api/ATEF").contentType(MediaType.APPLICATION_JSON).content(missingRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_REQUEST_DATA.name())));
        mockMvc.perform(post("/api/ATEF").contentType(MediaType.APPLICATION_JSON).content(overLappingRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_AIR_TEMP_RANGE.name())));
        mockMvc.perform(post("/api/ATEF").contentType(MediaType.APPLICATION_JSON).content(unOrderedRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_AIR_TEMP_RANGE.name())));

        mockMvc.perform(get(String.format("/api/ATEF/%d", entity.getId()))).andExpect(status().isOk());
        mockMvc.perform(get(String.format("/api/ATEF/%d", entity.getId()+1))).andExpect(status().isNotFound());

        mockMvc.perform(put(String.format("/api/ATEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(validRequestData)).andExpect(status().isOk());
        mockMvc.perform(put(String.format("/api/ATEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(invalidRequestData)).andExpect(status().isBadRequest());
        mockMvc.perform(put(String.format("/api/ATEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(missingRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_REQUEST_DATA.name())));
        mockMvc.perform(put(String.format("/api/ATEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(overLappingRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_AIR_TEMP_RANGE.name())));
        mockMvc.perform(put(String.format("/api/ATEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(unOrderedRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_AIR_TEMP_RANGE.name())));

        mockMvc.perform(delete(String.format("/api/ATEF/%d", entity.getId()))).andExpect(status().isOk());
        mockMvc.perform(delete(String.format("/api/ATEF/%d", entity.getId()))).andExpect(status().isNotFound());
    }

    @Test
    @Rollback
    public void testWSEFEndpoint() throws Exception {
        WindSpeedExtraFeeEntity entity = new WindSpeedExtraFeeEntity();
        String validRequestData =
                "{\"vehicleType\": \"SCOOTER\", \"conditionType\": \"FIRST_DEGREE\", \"minWindSpeedRequirement\": 10, \"maxWindSpeedRequirement\":20, \"amount\": 10}";
        String invalidRequestData =
                "{\"vehicleType\": \"BOAT\", \"conditionType\": \"FIRST_DEGREE\", \"minWindSpeedRequirement\": 10, \"maxWindSpeedRequirement\":20, \"amount\": 10}";
        String missingRequestData =
                "{\"vehicleType\": \"SCOOTER\", \"conditionType\": \"FIRST_DEGREE\", \"minWindSpeedRequirement\": 10, \"maxWindSpeedRequirement\":20}";
        String overLappingRequestData =
                "{\"vehicleType\": \"SCOOTER\", \"conditionType\": \"SECOND_DEGREE\", \"minWindSpeedRequirement\": 15, \"maxWindSpeedRequirement\":30, \"amount\": 10}";
        String unOrderedRequestData =
                "{\"vehicleType\": \"SCOOTER\", \"conditionType\": \"SECOND_DEGREE\", \"minWindSpeedRequirement\": 0, \"maxWindSpeedRequirement\":9, \"amount\": 10}";
        String negativeWindSpeedRequestData =
                "{\"vehicleType\": \"SCOOTER\", \"conditionType\": \"FIRST_DEGREE\", \"minWindSpeedRequirement\": -1, \"maxWindSpeedRequirement\":9, \"amount\": 10}";

        mockMvc.perform(post("/api/WSEF").contentType(MediaType.APPLICATION_JSON).content(validRequestData))
                .andExpect(status().isOk()).andDo(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    entity.setId(((Integer) JsonPath.read(jsonResponse, "$.id")).longValue());
                });
        mockMvc.perform(post("/api/WSEF").contentType(MediaType.APPLICATION_JSON).content(invalidRequestData)).andExpect(status().isBadRequest());
        mockMvc.perform(post("/api/WSEF").contentType(MediaType.APPLICATION_JSON).content(missingRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_REQUEST_DATA.name())));
        mockMvc.perform(post("/api/WSEF").contentType(MediaType.APPLICATION_JSON).content(overLappingRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_WIND_SPEED_RANGE.name())));
        mockMvc.perform(post("/api/WSEF").contentType(MediaType.APPLICATION_JSON).content(unOrderedRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_WIND_SPEED_RANGE.name())));
        mockMvc.perform(post("/api/WSEF").contentType(MediaType.APPLICATION_JSON).content(negativeWindSpeedRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_WIND_SPEED_RANGE.name())));

        mockMvc.perform(get(String.format("/api/WSEF/%d", entity.getId()))).andExpect(status().isOk());
        mockMvc.perform(get(String.format("/api/WSEF/%d", entity.getId()+1))).andExpect(status().isNotFound());

        mockMvc.perform(put(String.format("/api/WSEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(validRequestData)).andExpect(status().isOk());
        mockMvc.perform(put(String.format("/api/WSEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(invalidRequestData)).andExpect(status().isBadRequest());
        mockMvc.perform(put(String.format("/api/WSEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(missingRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_REQUEST_DATA.name())));
        mockMvc.perform(put(String.format("/api/WSEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(overLappingRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_WIND_SPEED_RANGE.name())));
        mockMvc.perform(put(String.format("/api/WSEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(unOrderedRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_WIND_SPEED_RANGE.name())));
        mockMvc.perform(put(String.format("/api/WSEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(negativeWindSpeedRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_WIND_SPEED_RANGE.name())));

        mockMvc.perform(delete(String.format("/api/WSEF/%d", entity.getId()))).andExpect(status().isOk());
        mockMvc.perform(delete(String.format("/api/WSEF/%d", entity.getId()))).andExpect(status().isNotFound());
    }

    @Test
    @Rollback
    public void testWPEFEndpoint() throws Exception {
        WeatherPhenomenonExtraFeeEntity entity = new WeatherPhenomenonExtraFeeEntity();
        String validRequestData =
                "{\"vehicleType\": \"SCOOTER\", \"conditionType\": \"FIRST_DEGREE\", \"phenomenonPattern\": \"(.*)(rain)(.*)\", \"amount\": 10}";
        String invalidRegexRequestData =
                "{\"vehicleType\": \"SCOOTER\", \"conditionType\": \"FIRST_DEGREE\", \"phenomenonPattern\": \"[a-z\", \"amount\": 10}";
        String missingDataRequest =
                "{\"conditionType\": \"FIRST_DEGREE\", \"phenomenonPattern\": \"(.*)(rain)(.*)\", \"amount\": 10}";

        mockMvc.perform(post("/api/WPEF").contentType(MediaType.APPLICATION_JSON).content(validRequestData))
                .andExpect(status().isOk()).andDo(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    entity.setId(((Integer) JsonPath.read(jsonResponse, "$.id")).longValue());
                });
        mockMvc.perform(post("/api/WPEF").contentType(MediaType.APPLICATION_JSON).content(invalidRegexRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_REGEX_PATTERN.name())));
        mockMvc.perform(post("/api/WPEF").contentType(MediaType.APPLICATION_JSON).content(missingDataRequest)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_REQUEST_DATA.name())));

        mockMvc.perform(get(String.format("/api/WPEF/%d", entity.getId()))).andExpect(status().isOk());
        mockMvc.perform(get(String.format("/api/WPEF/%d", entity.getId()+1))).andExpect(status().isNotFound());

        mockMvc.perform(put(String.format("/api/WPEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(validRequestData)).andExpect(status().isOk());
        mockMvc.perform(put(String.format("/api/WPEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(invalidRegexRequestData)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_REGEX_PATTERN.name())));
        mockMvc.perform(put(String.format("/api/WPEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(missingDataRequest)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_REQUEST_DATA.name())));

        mockMvc.perform(delete(String.format("/api/WPEF/%d", entity.getId()))).andExpect(status().isOk());
        mockMvc.perform(delete(String.format("/api/WPEF/%d", entity.getId()))).andExpect(status().isNotFound());
    }

    @Test
    @Rollback
    public void testRBFEndpoint() throws Exception {
        RegionalBaseFeeEntity entity = new RegionalBaseFeeEntity();
        String validRequestData =
                "{\"vehicleType\": \"SCOOTER\", \"cityType\": \"TALLINN\", \"amount\": 10}";
        String missingDataRequest =
                "{\"vehicleType\": \"SCOOTER\", \"cityType\": \"TALLINN\"}";
        String invalidDataRequest =
                "{\"vehicleType\": \"BOAT\", \"cityType\": \"TALLINN\", \"amount\": 10}";

        mockMvc.perform(post("/api/RBF").contentType(MediaType.APPLICATION_JSON).content(validRequestData))
                .andExpect(status().isOk()).andDo(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    entity.setId(((Integer) JsonPath.read(jsonResponse, "$.id")).longValue());
                });
        mockMvc.perform(post("/api/RBF").contentType(MediaType.APPLICATION_JSON).content(missingDataRequest)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_REQUEST_DATA.name())));
        mockMvc.perform(post("/api/RBF").contentType(MediaType.APPLICATION_JSON).content(invalidDataRequest)).andExpect(status().isBadRequest());

        mockMvc.perform(get(String.format("/api/RBF/%d", entity.getId()))).andExpect(status().isOk());
        mockMvc.perform(get(String.format("/api/RBF/%d", entity.getId()+1))).andExpect(status().isNotFound());

        mockMvc.perform(put(String.format("/api/RBF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(validRequestData)).andExpect(status().isOk());
        mockMvc.perform(put(String.format("/api/RBF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(missingDataRequest)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error.type", is(ApiExceptionErrorType.INVALID_REQUEST_DATA.name())));
        mockMvc.perform(put(String.format("/api/RBF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(invalidDataRequest)).andExpect(status().isBadRequest());

        mockMvc.perform(delete(String.format("/api/RBF/%d", entity.getId()))).andExpect(status().isOk());
        mockMvc.perform(delete(String.format("/api/RBF/%d", entity.getId()))).andExpect(status().isNotFound());
    }
}
