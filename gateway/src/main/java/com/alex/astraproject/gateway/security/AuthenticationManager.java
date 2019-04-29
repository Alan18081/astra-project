//package com.alex.astraproject.gateway.security;
//
//import com.alex.astraproject.gateway.services.JwtService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//@Component
//public class AuthenticationManager implements ReactiveAuthenticationManager {
//
//	@Autowired
//	private JwtService jwtService;
//
//	@Override
//	public Mono<Authentication> authenticate(Authentication authentication) {
//			String authToken = authentication.getCredentials().toString();
//
//			String username;
//
//			try {
//				username = jwtService.getUsernameFromToken(authToken);
//			} catch (Exception e) {
//				username = null;
//			}
//	}
//}
