package com.andersen.demoproject.service;

import com.andersen.demoproject.rest.response.CityResponse;
import com.andersen.demoproject.rest.response.CityPageResponse;
import com.andersen.demoproject.rest.request.UpdateCityRequest;
import org.springframework.data.domain.Pageable;

public interface CityService {
    CityPageResponse searchCitiesByNamePage(String name, Pageable pageable);
    CityResponse updateById(Integer id, UpdateCityRequest updateCityRequest);


}
