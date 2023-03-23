package com.andersen.demoproject.repository;

import com.andersen.demoproject.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
    Page<City> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
