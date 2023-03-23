package com.andersen.demoproject.service;

import com.andersen.demoproject.model.City;
import com.andersen.demoproject.repository.CityRepository;
import com.andersen.demoproject.rest.response.CityResponse;
import com.andersen.demoproject.rest.response.CityPageResponse;
import com.andersen.demoproject.rest.request.UpdateCityRequest;
import com.andersen.demoproject.service.exception.CityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository repository;

    public CityServiceImpl(CityRepository repository) {
        this.repository = repository;
    }

    public CityPageResponse searchCitiesByNamePage(String name, Pageable pageable) {
        return convertCityPageToDTO(repository.findByNameContainingIgnoreCase(name, pageable));
    }
    @Transactional
    public CityResponse updateById(Integer id, UpdateCityRequest updateCityRequest) {
        City city = repository.findById(id).orElseThrow(() -> new CityNotFoundException(id));

        city.setName(updateCityRequest.getName());
        city.setPictureUrl(updateCityRequest.getPictureUrl());

        return convertCityToDTO(repository.save(city));
    }

    private CityResponse convertCityToDTO(City city) {
        return new CityResponse(city.getId(), city.getName(), city.getPictureUrl());
    }

    private CityPageResponse convertCityPageToDTO(Page<City> city) {

        return new CityPageResponse(city.getTotalElements(),
                city.getContent().stream().map(this::convertCityToDTO).toList());
    }

}
