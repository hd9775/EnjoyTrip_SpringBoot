package com.gumi.enjoytrip.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoRestClient {
    private final WebClient webClient;
    public KakaoRestClient(@Value("${kakao.rest-api-key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://dapi.kakao.com/v2/local/geo/coord2address.json")
                .defaultHeader("Authorization", "KakaoAK " + apiKey)
                .build();
    }

    public String getAddress(double x, double y) {
        String address = "";
        try {
            String response = this.webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("x", x)
                            .queryParam("y", y)
                            .build())
                    .retrieve()
                    .bodyToFlux(String.class)
                    .blockFirst();

            JSONObject jsonObject = new JSONObject(response);
            JSONArray documents = jsonObject.getJSONArray("documents");
            JSONObject addressInfo = documents.getJSONObject(0).getJSONObject("road_address");
            address = addressInfo.getString("address_name");
        } catch (JSONException ignored) {

        }
        return address;
    }
}
