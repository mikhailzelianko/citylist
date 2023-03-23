package com.andersen.demoproject.rest.response;

import java.util.List;

public record  ErrorResponse(String timestamp, List<String> messages) {}
