package com.example.finalprojectaozcann.model.response;

import java.util.Set;

public record LoginResponse(String token, Set<String> roles) {
}
