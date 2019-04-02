package com.alex.astraproject.shared;

import com.alex.astraproject.shared.dto.companies.CompanyLoginDto;
import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.responses.JwtCompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Configuration
@ComponentScan("com.alex.astraproject.shared")
public class SharedContext {

    @Bean
    public Employee employee() {
        return new Employee();
    }

    @FeignClient("auth-service")
    public interface AuthClient {

        @RequestMapping(method = RequestMethod.POST, value = "/company/login")
        JwtCompanyResponse loginCompany(CompanyLoginDto dto);

    }
}
