package com.fooddelivery.courierfeeservice;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
        String requestData =
                "{\"vehicleType\": \"SCOOTER\", \"conditionType\": \"FIRST_DEGREE\", \"minTempRequirement\": -30, \"maxTempRequirement\":-10, \"amount\": 10}";
        mockMvc.perform(post("/api/ATEF").contentType(MediaType.APPLICATION_JSON).content(requestData))
                .andExpect(status().isOk()).andDo(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    entity.setId(((Integer) JsonPath.read(jsonResponse, "$.id")).longValue());
                });
        mockMvc.perform(get(String.format("/api/ATEF/%d", entity.getId()))).andExpect(status().isOk());
        mockMvc.perform(put(String.format("/api/ATEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(requestData)).andExpect(status().isOk());
        mockMvc.perform(delete(String.format("/api/ATEF/%d", entity.getId()))).andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void testWSEFEndpoint() throws Exception {
        WindSpeedExtraFeeEntity entity = new WindSpeedExtraFeeEntity();
        String requestData =
                "{\"vehicleType\": \"SCOOTER\", \"conditionType\": \"FIRST_DEGREE\", \"minWindSpeedRequirement\": 10, \"maxWindSpeedRequirement\":20, \"amount\": 10}";
        mockMvc.perform(post("/api/WSEF").contentType(MediaType.APPLICATION_JSON).content(requestData))
                .andExpect(status().isOk()).andDo(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    entity.setId(((Integer) JsonPath.read(jsonResponse, "$.id")).longValue());
                });
        mockMvc.perform(get(String.format("/api/WSEF/%d", entity.getId()))).andExpect(status().isOk());
        mockMvc.perform(put(String.format("/api/WSEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(requestData)).andExpect(status().isOk());
        mockMvc.perform(delete(String.format("/api/WSEF/%d", entity.getId()))).andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void testWPEFEndpoint() throws Exception {
        WeatherPhenomenonExtraFeeEntity entity = new WeatherPhenomenonExtraFeeEntity();
        String requestData =
                "{\"vehicleType\": \"SCOOTER\", \"conditionType\": \"FIRST_DEGREE\", \"phenomenonPattern\": \"(.*)(rain)(.*)\", \"amount\": 10}";
        mockMvc.perform(post("/api/WPEF").contentType(MediaType.APPLICATION_JSON).content(requestData))
                .andExpect(status().isOk()).andDo(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    entity.setId(((Integer) JsonPath.read(jsonResponse, "$.id")).longValue());
                });
        mockMvc.perform(get(String.format("/api/WPEF/%d", entity.getId()))).andExpect(status().isOk());
        mockMvc.perform(put(String.format("/api/WPEF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(requestData)).andExpect(status().isOk());
        mockMvc.perform(delete(String.format("/api/WPEF/%d", entity.getId()))).andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void testRBFEndpoint() throws Exception {
        RegionalBaseFeeEntity entity = new RegionalBaseFeeEntity();
        String requestData =
                "{\"vehicleType\": \"SCOOTER\", \"cityType\": \"TALLINN\", \"amount\": 10}";
        mockMvc.perform(post("/api/RBF").contentType(MediaType.APPLICATION_JSON).content(requestData))
                .andExpect(status().isOk()).andDo(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    entity.setId(((Integer) JsonPath.read(jsonResponse, "$.id")).longValue());
                });
        mockMvc.perform(get(String.format("/api/RBF/%d", entity.getId()))).andExpect(status().isOk());
        mockMvc.perform(put(String.format("/api/RBF/%d", entity.getId())).contentType(MediaType.APPLICATION_JSON).content(requestData)).andExpect(status().isOk());
        mockMvc.perform(delete(String.format("/api/RBF/%d", entity.getId()))).andExpect(status().isOk());
    }
}
