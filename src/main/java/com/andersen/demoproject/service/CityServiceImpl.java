package com.andersen.demoproject.service;

import com.andersen.demoproject.model.City;
import com.andersen.demoproject.repository.CityRepository;
import com.andersen.demoproject.rest.request.UpdateCityRequest;
import com.andersen.demoproject.service.exception.CityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CityService {
    private CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public Page<City> findByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }

    public City findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CityNotFoundException(id));
    }

    public City updateCityById(Long id, UpdateCityRequest updateCityRequest) {
        City city = repository.findById(id).orElseThrow(() -> new CityNotFoundException(id));

        city.setName(updateCityRequest.getName());
        city.setPictureUrl(updateCityRequest.getPictureUrl());

        return repository.save(city);
    }

    public City createCity(UpdateCityRequest updateCityRequest) {
        City city = new City();
        city.setName(updateCityRequest.getName());
        city.setPictureUrl(updateCityRequest.getPictureUrl());

        return repository.save(city);
    }

    public void deleteCityById(Long id) {
        Boolean bool = repository.existsById(id);
        if (bool) {
            repository.deleteById(id);
        } else {
            throw new CityNotFoundException(id);
        }
    }

}
