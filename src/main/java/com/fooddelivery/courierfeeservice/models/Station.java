package com.fooddelivery.courierfeeservice.models;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "station")
public class Station {
    private String name;

    private Long WMOCode;

    private double airTemp;

    private double windSpeed;

    private String phenomenon;

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "wmocode")
    public Long getWMOCode() {
        return WMOCode;
    }

    public void setWMOCode(Long WMOCode) {
        this.WMOCode = WMOCode;
    }

    @XmlElement(name = "airtemperature")
    public double getAirTemp() {
        return airTemp;
    }

    public void setAirTemp(double airTemp) {
        this.airTemp = airTemp;
    }

    @XmlElement(name = "windspeed")
    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @XmlElement(name = "phenomenon")
    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }
}
