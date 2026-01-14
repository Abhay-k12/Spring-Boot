package net.engineeringdigest.journalApp.apiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse{
    private Current current;
}

class Current{
    @JsonProperty("observation_time")
    private String observationTime;
    private int temperature;

    @JsonProperty("weather_code")
    private int weatherCode;

    @JsonProperty("wind_speed")
    private int windSpeed;

    @JsonProperty("wind_degree")
    private int windDegree;

    @JsonProperty("wind_dir")
    private String windDir;

    private int pressure;
    private int precip;
    private int humidity;
    private int visibility;
}






