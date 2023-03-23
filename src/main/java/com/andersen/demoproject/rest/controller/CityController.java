package com.andersen.demoproject.rest.controller;

import com.andersen.demoproject.rest.response.CityResponse;
import com.andersen.demoproject.rest.response.CityPageResponse;
import com.andersen.demoproject.rest.request.FindCitiesRequest;
import com.andersen.demoproject.rest.request.UpdateCityRequest;
import com.andersen.demoproject.service.CityService;
import com.andersen.demoproject.service.CityServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/city")
@Slf4j
public class CityController {

    private final CityService cityService;

    public CityController(CityServiceImpl cityService) {
        this.cityService = cityService;
    }

    @Operation(summary = "Find cities",
            description = "Return paged cities list filtered by name")
    @PostMapping(value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CityPageResponse> findCities(@RequestBody @Valid FindCitiesRequest request) {
        log.info("Find cities request: {}", request.toString());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by("id"));
        CityPageResponse result = cityService.searchCitiesByNamePage(request.getName(), pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Update city by ID",
            description = "Return updated city")
    @PutMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CityResponse> updateCityById(@PathVariable("id") int id, @RequestBody @Valid UpdateCityRequest updateCityRequest) {
        log.info("Update city. id={} request: {}", id, updateCityRequest.toString());
        return ResponseEntity.ok(cityService.updateById(id, updateCityRequest));
    }
}
