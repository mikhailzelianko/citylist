package com.andersen.demoproject.rest.response;

import java.util.List;

public record CityPageResponse(long totalElements, List<CityResponse> cities) {

}
