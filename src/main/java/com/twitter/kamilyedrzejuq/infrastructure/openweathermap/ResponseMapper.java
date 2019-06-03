package com.twitter.kamilyedrzejuq.infrastructure.openweathermap;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.twitter.kamilyedrzejuq.weather.domain.dto.WeatherInfoDTO;

import java.io.IOException;

class ResponseMapper extends StdDeserializer<WeatherInfoDTO> {

    ResponseMapper() {
        this(null);
    }

    ResponseMapper(Class<?> vc) {
        super(vc);
    }

    @Override
    public WeatherInfoDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String valueAsString = jsonParser.readValueAsTree().toString();
        return mapFromJson(valueAsString);
    }

    /**
     * Map a original message like below:
     * <pre>
     * {
     *    "coord":{
     *       "lon":21.01,
     *       "lat":52.23
     *    },
     *    "weather":[
     *       {
     *          "id":800,
     *          "main":"Clear",
     *          "description":"clear sky",
     *          "icon":"01d"
     *       }
     *    ],
     *    "base":"stations",
     *    "main":{
     *       "temp":27.29,
     *       "pressure":1018,
     *       "humidity":34,
     *       "temp_min":26.67,
     *       "temp_max":27.78
     *    },
     *    "visibility":10000,
     *    "wind":{
     *       "speed":4.1,
     *       "deg":110
     *    },
     *    "clouds":{
     *       "all":0
     *    },
     *    "dt":1559570394,
     *    "sys":{
     *       "type":1,
     *       "id":1713,
     *       "message":0.0066,
     *       "country":"PL",
     *       "sunrise":1559528377,
     *       "sunset":1559587728
     *    },
     *    "timezone":7200,
     *    "id":756135,
     *    "name":"Warsaw",
     *    "cod":200
     * }
     * </pre>
     * @param originalResponse - a json object
     * @return WeatherInfoDTO
     */
    private WeatherInfoDTO mapFromJson(String originalResponse) {
            JsonObject jobj = new Gson().fromJson(originalResponse, JsonObject.class);
            JsonObject main = jobj.get("main").getAsJsonObject();

            final String city = jobj.get("name").getAsString();
            final int temp = main.get("temp").getAsInt();
            final int pressure = main.get("pressure").getAsInt();
            final int humidity = main.get("humidity").getAsInt();

            return WeatherInfoDTO.builder()
                    .city(city)
                    .temp(temp)
                    .pressure(pressure)
                    .humidity(humidity)
                    .build();
    }
}
