package com.alex.astraproject.gateway.controllers;

import com.alex.astraproject.gateway.services.AuthService;
import com.alex.astraproject.shared.dto.common.LoginDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.responses.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/companies-service/employees")
public class EmployeeAuthController {

	@Autowired
	private AuthService<Employee> employeeAuthService;

	@PostMapping("/login")
	public Mono<JwtResponse<Employee>> login(@RequestBody @Valid LoginDto dto) {
		System.out.println("Trying to login dto");
		return employeeAuthService.createToken(dto);
	}

}
