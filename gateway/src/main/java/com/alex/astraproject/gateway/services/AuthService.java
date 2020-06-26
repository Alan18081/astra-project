package com.alex.astraproject.gateway.services;

import com.alex.astraproject.shared.dto.common.LoginDto;
import com.alex.astraproject.shared.responses.JwtResponse;
import reactor.core.publisher.Mono;

public interface AuthService<T> {

    Mono<JwtResponse<T>> createToken(LoginDto dto);

    Mono<T> verifyToken(String token);

}
