package com.api.springboottutorial.controllers;


import com.api.springboottutorial.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class ProductController {
    @GetMapping("")

    List<Product> getAllProducts() {
        /* Danh sách các list trong Java 8 ==> Arrays.asList*/
        return  Arrays.asList(
                new Product(1L,"Macbook", 2020,2400.0,""),
                new Product(2L,"HP", 2021,2500.0,"")
        );
    }
}
