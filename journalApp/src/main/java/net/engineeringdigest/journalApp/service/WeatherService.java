package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.apiResponse.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String API_KEY;

    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalAPI = API.replace("API_KEY",API_KEY).replace("CITY",city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET,null,WeatherResponse.class);

        //  Command to get Response Code of the response
        HttpStatusCode res = response.getStatusCode();

        return response.getBody();
    }

    /* METHOD TO SHOW THE USE-CASE OF POST API CALLING, ONLY FOR EXAMPLE*/
    public WeatherResponse getWeatherPostCallExample() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Key_attribute", "Value_attribute");

        User user = User.builder().userName("Abhay").password("Anshika12@").build();
        HttpEntity<User> httpEntity = new HttpEntity<>(user,httpHeaders);

        ResponseEntity<WeatherResponse> response= restTemplate.exchange(API,HttpMethod.POST,httpEntity,WeatherResponse.class);

        return response.getBody();

        /*
            "****** EVEN WE CAN USE THE STRING TO BUILD THE httpEntity ******"
            String userWithString  = " provide the json format here";
            HttpEntity<String> httpEntityWithString = new HttpEntity<>(userWithString);
        */
    }
}
