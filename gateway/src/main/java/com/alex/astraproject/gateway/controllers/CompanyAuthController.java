package com.alex.astraproject.gateway.controllers;

import com.alex.astraproject.gateway.services.AuthService;
import com.alex.astraproject.shared.dto.common.LoginDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.responses.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/companies")
public class CompanyAuthController {

	@Autowired
	private AuthService<Company> companyAuthService;

	@PostMapping("/login")
	public Mono<JwtResponse<Company>> login(@RequestBody @Valid LoginDto dto) {
		return companyAuthService.createToken(dto);
	}

}
