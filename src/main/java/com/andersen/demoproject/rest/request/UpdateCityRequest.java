package com.andersen.demoproject.rest.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateCityRequest {

    @NotNull(message = "City Name must be present")
    @Length(min = 1, max = 255, message = "City Name must be 1-255 characters")
    private String name;

    @NotNull(message = "City Picture URL must be present")
    @Length(min = 1, max = 1024, message = "City Pictures URL must be 1-1024 characters")
    private String pictureUrl;
}
