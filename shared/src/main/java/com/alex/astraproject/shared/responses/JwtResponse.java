package com.alex.astraproject.shared.responses;

import com.alex.astraproject.shared.entities.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse<T> {
    private String token;
    private long expiresIn;
    private T data;
}
