package com.alex.astraproject.apigateway.clients;

import com.alex.astraproject.shared.dto.companies.CompanyLoginDto;
import com.alex.astraproject.shared.dto.companies.VerifyCompanyTokenDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.responses.JwtCompanyResponse;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient("auth-service")
@RibbonClient(name = "companies-service")
public interface AuthClient {

    @RequestMapping(method = RequestMethod.POST, value = "/company/login")
    JwtCompanyResponse loginCompany(CompanyLoginDto dto);

    @RequestMapping(method = RequestMethod.POST, value = "/company/verifyToken")
    Company verifyToken(VerifyCompanyTokenDto dto);
}
