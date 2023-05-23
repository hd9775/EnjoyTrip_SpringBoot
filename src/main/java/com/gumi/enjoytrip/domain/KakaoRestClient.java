package com.gumi.enjoytrip.domain;

import com.gumi.enjoytrip.domain.tourinfo.dto.CoordinateDto;
import com.gumi.enjoytrip.domain.tourinfo.dto.PathDto;
import com.gumi.enjoytrip.domain.tourinfo.dto.RoadListDto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class KakaoRestClient {

    private final String apiKey;

    public KakaoRestClient(@Value("${kakao.rest-api-key}") String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAddress(double x, double y) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://dapi.kakao.com/v2/local/geo/coord2address.json")
                .defaultHeader("Authorization", "KakaoAK " + apiKey)
                .build();
        String address = "";
        try {
            String response = webClient.get()
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

    public CoordinateDto getCoordinate(String keyword) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://dapi.kakao.com/v2/local/search/keyword.json")
                .defaultHeader("Authorization", "KakaoAK " + apiKey)
                .build();
        CoordinateDto coordinateDto = new CoordinateDto();
        try {
            String response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("query", keyword)
                            .build())
                    .retrieve()
                    .bodyToFlux(String.class)
                    .blockFirst();

            JSONObject jsonObject = new JSONObject(response);
            JSONArray documents = jsonObject.getJSONArray("documents");
            JSONObject coordinateInfo = documents.getJSONObject(0);
            coordinateDto.setLatitude(coordinateInfo.getDouble("y"));
            coordinateDto.setLongitude(coordinateInfo.getDouble("x"));
        } catch (JSONException ignored) {

        }
        return coordinateDto;
    }

    public PathDto getPath(CoordinateDto start, CoordinateDto end) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://apis-navi.kakaomobility.com/v1/directions")
                .defaultHeader("Authorization", "KakaoAK " + apiKey)
                .build();
        PathDto pathDto = new PathDto();
        List<RoadListDto> roadList = new ArrayList<>();
        pathDto.setRoadList(roadList);

        try {
            String response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("origin", start.getLongitude() + "," + start.getLatitude())
                            .queryParam("destination", end.getLongitude() + "," + end.getLatitude())
                            .build())
                    .retrieve()
                    .bodyToFlux(String.class)
                    .blockFirst();

            JSONObject jsonObject = new JSONObject(response);
            JSONObject route = jsonObject.getJSONArray("routes").getJSONObject(0);
            JSONObject summary = route.getJSONObject("summary");
            JSONObject fare = summary.getJSONObject("fare");
            JSONArray roads = route.getJSONArray("sections").getJSONObject(0).getJSONArray("roads");
            pathDto.setTime(summary.getInt("duration"));
            pathDto.setDistance(summary.getInt("distance"));
            pathDto.setTaxiFare(fare.getInt("taxi"));
            pathDto.setTollFare(fare.getInt("toll"));
            pathDto.setLongitude((start.getLongitude() + end.getLongitude()) / 2);
            pathDto.setLatitude((start.getLatitude() + end.getLatitude()) / 2);
            for (int i = 0; i < roads.length(); i++) {
                RoadListDto roadListDto = new RoadListDto();
                List<CoordinateDto> coordinateDtos = new ArrayList<>();
                JSONObject road = roads.getJSONObject(i);
                JSONArray vertexes = road.getJSONArray("vertexes");
                int trafficState = road.getInt("traffic_state");
                for (int j = 0; j < vertexes.length(); j += 2) {
                    CoordinateDto coordinateDto = new CoordinateDto();
                    coordinateDto.setLongitude(vertexes.getDouble(j));
                    coordinateDto.setLatitude(vertexes.getDouble(j + 1));
                    coordinateDtos.add(coordinateDto);
                }
                roadListDto.setTrafficState(trafficState);
                roadListDto.setCoordinates(coordinateDtos);
                roadList.add(roadListDto);
            }
        } catch (JSONException ignored) {

        }
        return pathDto;
    }
}
