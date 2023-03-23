package com.andersen.demoproject.service;

import com.andersen.demoproject.model.City;
import com.andersen.demoproject.repository.CityRepository;
import com.andersen.demoproject.rest.response.CityPageResponse;
import com.andersen.demoproject.rest.response.CityResponse;
import com.andersen.demoproject.rest.request.UpdateCityRequest;
import com.andersen.demoproject.service.exception.CityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CityServiceTest {

    private static final int CITY_ID_1 = 10;
    private static final int CITY_ID_2 = 20;
    private static final String CITY_NAME_1 = "Minsk";
    private static final String CITY_NAME_2 = "Vilnius";

    private static final String CITY_PICTURE_URL_1 = "http://url.com/1.jpg";
    private static final String CITY_PICTURE_URL_2 = "http://url.com/2.jpg";

    @Mock
    private CityRepository cityRepository;

    private CityServiceImpl cityService;

    @BeforeEach
    void setUp()
    {
        this.cityService
                = new CityServiceImpl(cityRepository);
    }

    @DisplayName("Existed city should be updated")
    @Test
    public void whenUpdateById_thenCityUpdated() {

        City existedCity = new City();
        existedCity.setName(CITY_NAME_1);
        existedCity.setPictureUrl(CITY_PICTURE_URL_1);
        existedCity.setId(CITY_ID_1);

        when(cityRepository.findById(CITY_ID_1)).thenReturn(Optional.of(existedCity));
        when(cityRepository.save(any())).then(i -> i.getArguments()[0]);

        UpdateCityRequest updateCityRequest = new UpdateCityRequest();
        updateCityRequest.setName(CITY_NAME_2);
        updateCityRequest.setPictureUrl(CITY_PICTURE_URL_2);

        CityResponse city  = cityService.updateById(CITY_ID_1, updateCityRequest);

        verify(cityRepository, times(1)).save(any());

        assertEquals(CITY_ID_1, city.id(), "id should be the same");
        assertEquals(CITY_NAME_2, city.name(), "city name should be updated");
        assertEquals(CITY_PICTURE_URL_2, city.pictureUrl(), "city picture should be updated");
    }

    @DisplayName("non-existed city update should generate exception")
    @Test
    public void whenUpdateById_thenNotFoundException() {

        when(cityRepository.findById(CITY_ID_1)).thenReturn(Optional.empty());

        UpdateCityRequest updateCityRequest = new UpdateCityRequest();
        updateCityRequest.setName(CITY_NAME_2);
        updateCityRequest.setPictureUrl(CITY_PICTURE_URL_2);

        assertThrows(CityNotFoundException.class,
                () -> cityService.updateById(CITY_ID_1, updateCityRequest));
    }

    @DisplayName("Find cities should return cities list ")
    @Test
    public void whenSearchCitiesByNamePage_thenCitiesList() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        List<City> repositoryCities = getCitiesFromRepositoryList();
        Page<City> page = new PageImpl<>(repositoryCities, pageable, repositoryCities.size());

        when(cityRepository.findByNameContainingIgnoreCase("", pageable)).thenReturn(page);

        CityPageResponse response = cityService.searchCitiesByNamePage("", pageable);

        assertEquals(response.totalElements(), page.getTotalElements());

        List<CityResponse> cities = response.cities();
        assertEquals(cities.size(), repositoryCities.size(), "Lists size should be equals");

        CityResponse city1 = cities.get(0);
        City repositoryCity1 = repositoryCities.get(0);
        assertEquals(city1.id(), repositoryCity1.getId(), "ID should be equals");
        assertEquals(city1.name(), repositoryCity1.getName(), "Name should be equals");
        assertEquals(city1.pictureUrl(), repositoryCity1.getPictureUrl(), "Picture URL should be equals");

        CityResponse city2 = cities.get(1);
        City repositoryCity2 = repositoryCities.get(1);
        assertEquals(city2.id(), repositoryCity2.getId(), "ID should be equals");
        assertEquals(city2.name(), repositoryCity2.getName(), "Name should be equals");
        assertEquals(city2.pictureUrl(), repositoryCity2.getPictureUrl(), "Picture URL should be equals");
    }

    private List<City> getCitiesFromRepositoryList() {
        City city1 = new City();
        city1.setId(CITY_ID_1);
        city1.setName(CITY_NAME_1);
        city1.setPictureUrl(CITY_PICTURE_URL_1);

        City city2 = new City();
        city2.setId(CITY_ID_2);
        city2.setName(CITY_NAME_2);
        city2.setPictureUrl(CITY_PICTURE_URL_2);

        return List.of(city1, city2);
    }


}
