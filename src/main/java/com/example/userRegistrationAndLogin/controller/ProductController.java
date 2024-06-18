package com.example.userRegistrationAndLogin.controller;

import com.example.userRegistrationAndLogin.entity.Product;
import com.example.userRegistrationAndLogin.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/category/{categoryName}")
    public String showProductsByCategory(@PathVariable String categoryName, Model model) {
        List<Product> products = productService.getProductsByCategory(categoryName);
        model.addAttribute("products", products);
        return "products/category";
    }

    @GetMapping("/all")
    public String showAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products/all";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("productId") Long productId, HttpSession session) {
        // Implement add to cart logic here
        return "redirect:/home";
    }
}
