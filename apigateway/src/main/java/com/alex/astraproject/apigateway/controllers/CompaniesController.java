package com.alex.astraproject.apigateway.controllers;

import com.alex.astraproject.apigateway.clients.CompaniesClient;
import com.alex.astraproject.shared.entities.Company;
//import com.alex.astraproject.shared.feign.clients.CompaniesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    @Autowired
    private CompaniesClient companiesClient;

    @GetMapping
    public Company findMany(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return companiesClient.findById(25);
    }
}

