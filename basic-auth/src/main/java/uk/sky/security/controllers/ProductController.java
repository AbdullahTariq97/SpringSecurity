package uk.sky.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.sky.security.models.Product;

import java.util.List;

@RestController
@RequestMapping("/private")
public class ProductController {

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return List.of(new Product(1,"cup",1.50),
                new Product(2, "wipes", 2.00),
                new Product(3, "milk", 1.90));
    }

}

