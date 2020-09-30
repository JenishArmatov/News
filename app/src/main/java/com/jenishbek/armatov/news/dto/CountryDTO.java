package com.jenishbek.armatov.news.dto;

public class CountryDTO {
    String city;
    String cityID;

    public CountryDTO(String cityID, String city) {
        this.city = city;
        this.cityID = cityID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }
}
