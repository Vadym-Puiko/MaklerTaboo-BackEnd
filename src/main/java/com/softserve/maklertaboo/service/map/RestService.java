package com.softserve.maklertaboo.service.map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;
import java.util.Collections;

@Service
public class RestService {
    private final static String BING_MAPS_API_LAT_URL = "http://dev.virtualearth.net/REST/v1/Locations";
    private final RestTemplate restTemplate;

    @Value("${bingApiKey}")
    private String bingApiKey;

    @Autowired
    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(500))
                .setReadTimeout(Duration.ofSeconds(500))
                .build();
    }

    public String getPostWithCustomHeaders(AddressRequest addressRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("x-request-src", "desktop");

        URI uri = getUriString(addressRequest);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        System.out.println(uri);
        ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                String.class
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return response.getBody();
        }
    }

    private URI getUriString(AddressRequest addressRequest) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BING_MAPS_API_LAT_URL)
                .queryParam("countryRegion", addressRequest.getCountryRegion())
                .queryParam("adminDistrict", addressRequest.getAdminDistrict())
                .queryParam("locality", addressRequest.getLocality())
                .queryParam("postalCode", String.valueOf(addressRequest.getPostalCode()))
                .queryParam("addressLine", addressRequest.getAddressLine())
                .queryParam("key", bingApiKey);
        return builder.build().encode().toUri();
    }
}
