package com.fooddelivery.courierfeeservice.models;


import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "observations")
public class WeatherDataResponse {
    private Long timestamp;

    private List<Station> stationList;

    public Long getTimestamp() {
        return timestamp;
    }

    @XmlAttribute(name = "timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @XmlElement(name = "station")
    public List<Station> getStationList() {
        return stationList;
    }

    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }
}
