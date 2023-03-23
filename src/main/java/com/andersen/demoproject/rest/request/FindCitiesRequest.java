package com.andersen.demoproject.rest.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FindCitiesRequest {
    @NotNull(message = "Page number must be present")
    private int page;
    @NotNull(message = "Page size must be present")
    private int size;
    private String name;
}
