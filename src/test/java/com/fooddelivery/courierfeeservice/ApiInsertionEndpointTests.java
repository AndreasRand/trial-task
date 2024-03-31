package com.fooddelivery.courierfeeservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        String requestData =
                "{\"vehicleType\": 1, \"conditionType\": 0, \"tempRequirement\": -30, \"amount\": 10}";
        mockMvc.perform(put("/api/insertATEF").contentType("application/json").content(requestData)).andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void testWSEFEndpoint() throws Exception {
        String requestData =
                "{\"vehicleType\": 1, \"conditionType\": 0, \"windSpeedRequirement\": 25, \"amount\": 10}";
        mockMvc.perform(put("/api/insertWSEF").contentType("application/json").content(requestData)).andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void testWPEFEndpoint() throws Exception {
        String requestData =
                "{\"vehicleType\": 1, \"conditionType\": 0, \"phenomenonPattern\": \"(.*)(rain)(.*)\", \"amount\": 10}";
        mockMvc.perform(put("/api/insertWPEF").contentType("application/json").content(requestData)).andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void testRBFEndpoint() throws Exception {
        String requestData =
                "{\"vehicleType\": 1, \"cityType\": 0, \"amount\": 10}";
        mockMvc.perform(put("/api/insertRBF").contentType("application/json").content(requestData)).andExpect(status().isOk());
    }
}
