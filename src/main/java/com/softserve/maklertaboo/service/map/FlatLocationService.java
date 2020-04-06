package com.softserve.maklertaboo.service.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.maklertaboo.entity.Address;
import com.softserve.maklertaboo.entity.flat.FlatLocation;
import com.softserve.maklertaboo.exception.exceptions.InvalidJsonParsingStringException;
import com.softserve.maklertaboo.repository.FlatLocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.softserve.maklertaboo.constant.ErrorMessage.JSON_PARSING_EXCEPTION;

@Slf4j
@Service
public class FlatLocationService {

    private RestService restService;
    private FlatLocationRepository flatLocationRepository;

    @Autowired
    public FlatLocationService(RestService restService, FlatLocationRepository flatLocationRepository) {
        this.restService = restService;
        this.flatLocationRepository = flatLocationRepository;
    }

    public FlatLocation generateLocation(Address address) {
        AddressRequest addressRequest = convertToAddressRequest(address);
        String jsonResult = restService.getPostWithCustomHeaders(addressRequest);
        return getFlatLocation(jsonResult);
    }

    private FlatLocation getFlatLocation(String jsonResult) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root;
        try {
            root = objectMapper.readTree(jsonResult);
        } catch (JsonProcessingException e) {
            log.error(JSON_PARSING_EXCEPTION, e);
            throw new InvalidJsonParsingStringException(JSON_PARSING_EXCEPTION);
        }
        FlatLocation flatLocation = new FlatLocation();
        insertLocations(flatLocation, root);
        return flatLocation;
    }

    private void insertLocations(FlatLocation flatLocation, JsonNode root) {
        flatLocation.setLatitude(root
                .get("resourceSets")
                .get(0)
                .get("resources")
                .get(0)
                .get("point")
                .get("coordinates")
                .get(0)
                .asText());
        flatLocation.setLongitude(root
                .get("resourceSets")
                .get(0)
                .get("resources")
                .get(0)
                .get("point")
                .get("coordinates")
                .get(1)
                .asText());
    }

    private AddressRequest convertToAddressRequest(Address address) {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setAddressLine(address.getStreet() + " vulytsia, " + address.getHouseNumber());
        addressRequest.setAdminDistrict("Lviv Oblast");
        addressRequest.setCountryRegion("UA");
        addressRequest.setLocality("Lviv");
        addressRequest.setPostalCode(79000);
        return addressRequest;
    }

    public List<FlatLocation> getFlatLocations() {
        List<FlatLocation> flatLocations = flatLocationRepository.findAll();
        return flatLocations;
    }
}
