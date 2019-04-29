//package com.alex.astraproject.gateway.security;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class User implements UserDetails {
//	@Setter
//	private String username;
//
//	@Setter
//	private String password;
//
//	@Setter
//	private boolean enabled;
//
//	@Getter
//	@Setter
//	private List<Role> roles;
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return this.roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList());
//	}
//
//	@Override
//	public String getPassword() {
//		return this.password;
//	}
//
//	@Override
//	public String getUsername() {
//		return this.username;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return false;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return false;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return false;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return this.enabled;
//	}
//}
